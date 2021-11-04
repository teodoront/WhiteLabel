package br.com.orlandoneto.whitelabel.data

import android.net.Uri
import br.com.orlandoneto.whitelabel.BuildConfig
import br.com.orlandoneto.whitelabel.domain.model.Product
import br.com.orlandoneto.whitelabel.util.COLLECTION_PRODUCTS
import br.com.orlandoneto.whitelabel.util.COLLECTION_ROOT
import br.com.orlandoneto.whitelabel.util.STORAGE_IMAGES
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class FirebaseProductDataSource @Inject constructor(
    firebaseFirestore: FirebaseFirestore,
    firebaseStorage: FirebaseStorage
) : ProductDataSource{

    private val documentReference = firebaseFirestore
        .document("$COLLECTION_ROOT/${BuildConfig.FIREBASE_FLAVOR_COLLECTION}")

    private val storeReference = firebaseStorage.reference

    override suspend fun getProducts(): List<Product> {
        return suspendCoroutine {continuation ->
            val productsReference = documentReference.collection(COLLECTION_PRODUCTS)
            productsReference.get().addOnSuccessListener {
                val products = mutableListOf<Product>()
                for (document in it){
                    document.toObject(Product::class.java).run{
                        products.add(this)
                    }
                }

                continuation.resumeWith(Result.success(products))
            }

            productsReference.get().addOnFailureListener {exception ->
                continuation.resumeWith(Result.failure(exception))

            }
        }
    }

    override suspend fun uploadProductImage(imageUri: Uri): String {
        return suspendCoroutine { continuation ->
            val randomKey = UUID.randomUUID()
            val childReference = storeReference.child(
                "$STORAGE_IMAGES/${BuildConfig.FIREBASE_FLAVOR_COLLECTION}/$randomKey"
            )

            childReference.putFile(imageUri).addOnSuccessListener {taskSnapshot ->


            }.addOnFailureListener{exception ->
                continuation.resumeWith(Result.failure(exception))
            }

        }
    }

    override suspend fun createProduct(product: Product): Product {
        return suspendCoroutine { continuation ->
            documentReference.collection(COLLECTION_PRODUCTS).document(System.currentTimeMillis().toString())
                .set(product)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(product))
                }.addOnFailureListener{
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }
}
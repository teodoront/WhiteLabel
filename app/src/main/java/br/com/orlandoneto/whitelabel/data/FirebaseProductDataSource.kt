package br.com.orlandoneto.whitelabel.data

import android.net.Uri
import br.com.orlandoneto.whitelabel.BuildConfig
import br.com.orlandoneto.whitelabel.domain.model.Product
import br.com.orlandoneto.whitelabel.util.COLLECTION_PRODUCTS
import br.com.orlandoneto.whitelabel.util.COLLECTION_ROOT
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlin.coroutines.suspendCoroutine

class FirebaseProductDataSource(
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
        }//finalizando a implementação do FirebaseDataSource
    }

    override suspend fun uploadProductImage(imageUri: Uri): String {
        TODO("Not yet implemented")
    }

    override suspend fun createProduct(product: Product): Product {
        TODO("Not yet implemented")
    }
}
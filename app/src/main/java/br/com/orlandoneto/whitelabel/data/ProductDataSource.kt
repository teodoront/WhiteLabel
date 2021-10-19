package br.com.orlandoneto.whitelabel.data

import android.net.Uri
import br.com.orlandoneto.whitelabel.domain.model.Product

interface ProductDataSource {

    suspend fun getProducts(): List<Product>
    suspend fun uploadProductImage(imageUri: Uri): String
    suspend fun createProduct(product: Product): Product

}
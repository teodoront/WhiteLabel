package br.com.orlandoneto.whitelabel.data

import android.net.Uri
import br.com.orlandoneto.whitelabel.domain.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(private val dataSource: ProductDataSource) {

    suspend fun getProducts(): List<Product> = dataSource.getProducts()
    suspend fun uploadProductImage(imageUri: Uri): String = dataSource.uploadProductImage(imageUri)
    suspend fun createProduct(product: Product): Product = dataSource.createProduct(product)

}
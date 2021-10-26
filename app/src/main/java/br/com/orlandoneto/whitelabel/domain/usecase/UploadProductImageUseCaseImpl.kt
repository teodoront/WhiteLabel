package br.com.orlandoneto.whitelabel.domain.usecase

import android.net.Uri
import br.com.orlandoneto.whitelabel.data.ProductRepository

class UploadProductImageUseCaseImpl(
    private val productRepository: ProductRepository

) : UploadProductImageUseCase{
    override suspend fun invoke(imageUri: Uri): String {
        return productRepository.uploadProductImage(imageUri)
    }
}
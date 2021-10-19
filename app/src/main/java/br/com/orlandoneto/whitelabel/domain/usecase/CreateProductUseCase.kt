package br.com.orlandoneto.whitelabel.domain.usecase

import android.net.Uri
import br.com.orlandoneto.whitelabel.domain.model.Product

interface CreateProductUseCase {

    suspend operator fun invoke(description: String, price: Double, imageUri: Uri): Product

}
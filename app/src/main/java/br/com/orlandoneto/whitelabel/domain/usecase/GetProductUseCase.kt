package br.com.orlandoneto.whitelabel.domain.usecase

import br.com.orlandoneto.whitelabel.domain.model.Product

interface GetProductUseCase {

    suspend operator fun invoke(): List<Product>

}
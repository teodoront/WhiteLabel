package br.com.orlandoneto.whitelabel.domain.usecase

import br.com.orlandoneto.whitelabel.data.ProductRepository
import br.com.orlandoneto.whitelabel.domain.model.Product

class GetProductUseCaseImpl(
    private val productRepository: ProductRepository
) : GetProductUseCase {
    override suspend fun invoke(): List<Product> {
        return productRepository.getProducts()
    }
}
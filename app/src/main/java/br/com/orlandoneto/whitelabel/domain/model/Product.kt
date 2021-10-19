package br.com.orlandoneto.whitelabel.domain.model

data class Product(

    val id: String = "",
    val description: String = "",
    val price: Double = 0.0,
    var imageUrl: String = ""

)

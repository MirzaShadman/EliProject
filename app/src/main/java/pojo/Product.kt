package pojo

data class Product(val productCode: String, val productName: String,
                   val productPrice: String, val imageUrl: String, var qty: Int? = 1)

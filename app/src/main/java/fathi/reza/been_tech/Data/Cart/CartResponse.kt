package fathi.reza.been_tech.Data.Cart

data class CartResponse(
    var message: List<CarrtItem>,
    val status: String,
    val totalPrice: Long,
    val totalDiscount:Int
)
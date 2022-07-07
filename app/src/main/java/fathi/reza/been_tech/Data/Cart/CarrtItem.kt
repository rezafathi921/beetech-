package fathi.reza.been_tech.Data.Cart

data class CarrtItem(
    val basket_id: String,
    val benefitplus: List<String>,
    val colors: List<String>,
    val companyname:String,
    var count: String,
    val discount: String,
    val garantee: List<String>,
    val order_id: String,
    val pay: String,
    val pic: String,
    val price: String,
    val productTitle: String,
    val product_id: String,
    val stock: String,
    val typesend: List<String>,
    val user_email: String,
    val user_id: String,
    val tedad_mojood:String,
    var progressBar:Boolean=false
)
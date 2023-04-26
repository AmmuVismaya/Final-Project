package com.example.barterbay.model

data class ProductModel(
    var user_id: String = "",
    var user_name: String = "",
    var title: String = "",
    var price: String = "",
    var description: String = "",
    var stock_quantity: String = "",
    var image: String = "",
    var id: String = "",

)
data class Profile(
    var ProfileImage :String? ="",
    var Email :String? ="",
    var FirstName :String? ="",
    var LastName :String? =""
)
data class  ProductDetails(
    var ProductName:String? = "",
    var ProductPrice:String? = "",
    var ProductImage:String?  = ""
)



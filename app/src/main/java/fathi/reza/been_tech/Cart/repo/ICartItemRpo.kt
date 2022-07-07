package fathi.reza.been_tech.Cart.repo

import fathi.reza.been_tech.Data.Cart.CartResponse
import fathi.reza.been_tech.Data.CartItemCount
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import io.reactivex.Single


interface ICartItemRpo {
    fun cartItemResponse(): Single<CartResponse>
    fun addToBasket(productId:String): Single<LoginMessage>
    fun changeCartItemCart(productId: String,count:Int): Single<LoginMessage>
    fun removeCartItemFromBasket(productId: String): Single<LoginMessage>
    fun getCartItemCount():Single<CartItemCount>
}
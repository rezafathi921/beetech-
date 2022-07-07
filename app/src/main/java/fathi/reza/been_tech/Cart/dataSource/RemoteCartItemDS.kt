package fathi.reza.been_tech.Cart.dataSource

import fathi.reza.been_tech.Data.Cart.CartResponse
import fathi.reza.been_tech.Data.CartItemCount
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.Retrofit.ApiService
import io.reactivex.Single

class RemoteCartItemDS(val apiService: ApiService) :ICartItemDS {
    override fun cartItemResponse(): Single<CartResponse> = apiService.cartItemResponse()

    override fun addToBasket(productId: String): Single<LoginMessage> =apiService.addToBasket(productId)

    override fun changeCartItemCart(productId: String, count: Int): Single<LoginMessage> = apiService.changeCartItemCart(productId, count)

    override fun removeCartItemFromBasket(productId: String): Single<LoginMessage> =apiService.removeCartItemFromBasket(productId)
    override fun getCartItemCount(): Single<CartItemCount> = apiService.getCartItemCount()
}
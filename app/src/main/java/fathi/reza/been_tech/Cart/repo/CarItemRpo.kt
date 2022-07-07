package fathi.reza.been_tech.Cart.repo

import fathi.reza.been_tech.Cart.dataSource.ICartItemDS
import fathi.reza.been_tech.Cart.dataSource.RemoteCartItemDS
import fathi.reza.been_tech.Data.Cart.CartResponse
import fathi.reza.been_tech.Data.CartItemCount
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import io.reactivex.Single

class CarItemRpo(val remoteCartItemDS:ICartItemDS) :ICartItemRpo{
    override fun cartItemResponse(): Single<CartResponse> = remoteCartItemDS.cartItemResponse()

    override fun addToBasket(productId: String): Single<LoginMessage> = remoteCartItemDS.addToBasket(productId)

    override fun changeCartItemCart(productId: String, count: Int): Single<LoginMessage> =remoteCartItemDS.changeCartItemCart(productId, count)

    override fun removeCartItemFromBasket(productId: String): Single<LoginMessage> = remoteCartItemDS.removeCartItemFromBasket(productId)
    override fun getCartItemCount(): Single<CartItemCount> = remoteCartItemDS.getCartItemCount()
}
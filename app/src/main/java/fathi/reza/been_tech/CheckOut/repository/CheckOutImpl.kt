package fathi.reza.been_tech.CheckOut.repository

import fathi.reza.been_tech.CheckOut.datasource.ICheckoutDs
import fathi.reza.been_tech.CheckOut.datasource.RemoteCheckOut
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import io.reactivex.Single

class CheckOutImpl(val remoteCheckOut: ICheckoutDs):ICheckout {
    override fun checkout(orderId: String): Single<LoginMessage> =remoteCheckOut.checkout(orderId)
}
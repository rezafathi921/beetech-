package fathi.reza.been_tech.CheckOut.repository

import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import io.reactivex.Single

interface ICheckout {
    fun checkout(orderId:String):Single<LoginMessage>
}
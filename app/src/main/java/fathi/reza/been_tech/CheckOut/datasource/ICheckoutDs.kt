package fathi.reza.been_tech.CheckOut.datasource

import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import io.reactivex.Single

interface ICheckoutDs {
    fun checkout(orderId:String):Single<LoginMessage>
}
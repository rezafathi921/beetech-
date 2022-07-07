package fathi.reza.been_tech.CheckOut.datasource

import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.Retrofit.ApiService
import io.reactivex.Single

class RemoteCheckOut(val apiService: ApiService):ICheckoutDs {
    override fun checkout(orderId: String): Single<LoginMessage> = apiService.checkout(orderId)
}
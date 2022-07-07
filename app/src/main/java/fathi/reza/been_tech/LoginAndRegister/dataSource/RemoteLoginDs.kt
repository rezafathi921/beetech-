package fathi.reza.been_tech.LoginAndRegister.dataSource

import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.Retrofit.ApiService
import io.reactivex.Single

class RemoteLoginDs(private val apiService: ApiService):ILoginDs {
    override fun login(email: String?, pass: String?): Single<LoginMessage> = apiService.login(email,pass)

    override fun register(email: String, pass: String): Single<LoginMessage> = apiService.register(email, pass)

    override fun saveToken(token: String, refresh_token: String) {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun checkLogin():Boolean {
        TODO("Not yet implemented")
    }

    override fun addToFav(id: String): Single<LoginMessage> =apiService.addToFav(id)

    override fun deleteFromFav(id: String): Single<LoginMessage> = apiService.deleteFromFav(id)
}
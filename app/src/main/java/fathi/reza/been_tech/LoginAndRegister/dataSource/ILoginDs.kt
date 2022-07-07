package fathi.reza.been_tech.LoginAndRegister.dataSource

import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import io.reactivex.Single

interface ILoginDs {
    fun login(email: String?, pass: String?): Single<LoginMessage>
    fun register(email: String, pass: String): Single<LoginMessage>
    fun saveToken(token: String, refresh_token: String)
    fun loadToken()
    fun checkLogin(): Boolean
    fun addToFav(id: String): Single<LoginMessage>
    fun deleteFromFav(id: String): Single<LoginMessage>

}
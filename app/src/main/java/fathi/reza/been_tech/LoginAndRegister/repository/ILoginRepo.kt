package fathi.reza.been_tech.LoginAndRegister.repository

import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import io.reactivex.Single

interface ILoginRepo {
    fun login(email: String?, pass: String?): Single<LoginMessage>
    fun register(email: String, pass: String): Single<LoginMessage>
    fun loadToken()
    fun checkLogin(): Boolean
    fun addToFav(id: String): Single<LoginMessage>
    fun deleteFromFav(id: String): Single<LoginMessage>
}
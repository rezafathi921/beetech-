package fathi.reza.been_tech.LoginAndRegister.dataSource

import android.content.SharedPreferences
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.LoginAndRegister.TokenHolder
import io.reactivex.Single

class LocalLoginDs(private val sharedPreferences: SharedPreferences):ILoginDs {
    override fun login(email: String?, pass: String?): Single<LoginMessage> {
        TODO("Not yet implemented")
    }

    override fun register(email: String, pass: String): Single<LoginMessage> {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refresh_token: String) {
       sharedPreferences.edit().apply {
           putString("token",token)
           putString("refresh_token",refresh_token)
       }.apply()
    }

    override fun loadToken() {
        TokenHolder.updateToken(
            sharedPreferences.getString("token",""),
            sharedPreferences.getString("refresh_token","")
        )

    }

    override fun checkLogin():Boolean {
        return if (sharedPreferences.getString("token","") !=""){
            true
        }else{
           return false
        }
    }

    override fun addToFav(id: String): Single<LoginMessage> {
        TODO("Not yet implemented")
    }

    override fun deleteFromFav(id: String): Single<LoginMessage> {
        TODO("Not yet implemented")
    }


}
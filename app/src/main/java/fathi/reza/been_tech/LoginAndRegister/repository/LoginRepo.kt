package fathi.reza.been_tech.LoginAndRegister.repository

import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.LoginAndRegister.dataSource.ILoginDs
import fathi.reza.been_tech.LoginAndRegister.dataSource.LocalLoginDs
import fathi.reza.been_tech.LoginAndRegister.dataSource.RemoteLoginDs
import io.reactivex.Single

class LoginRepo(private val remoteLoginDs: ILoginDs, private val localLoginDs: ILoginDs):ILoginRepo {
    override fun login(email: String?, pass: String?): Single<LoginMessage> {
        return remoteLoginDs.login(email, pass).doOnSuccess {
            if (it.status=="success") {
                localLoginDs.saveToken(it.message, it.refresh_token)
                localLoginDs.loadToken()
            }

        }
    }

    override fun register(email: String, pass: String): Single<LoginMessage> {
        return remoteLoginDs.register(email, pass).doOnSuccess {
            if (it.status=="success"){
                localLoginDs.saveToken(it.message,it.refresh_token)
                localLoginDs.loadToken()
            }
        }

    }


    override fun loadToken() = localLoginDs.loadToken()

    override fun checkLogin():Boolean =localLoginDs.checkLogin()

    override fun addToFav(id: String): Single<LoginMessage> = remoteLoginDs.addToFav(id)

    override fun deleteFromFav(id: String): Single<LoginMessage> = remoteLoginDs.deleteFromFav(id)


}
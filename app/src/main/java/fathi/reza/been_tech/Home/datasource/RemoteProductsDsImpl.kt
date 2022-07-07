package fathi.reza.been_tech.Home.datasource

import fathi.reza.been_tech.Data.DefItemProduct
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.Data.Products
import fathi.reza.been_tech.Retrofit.ApiService
import io.reactivex.Single

class RemoteProductsDsImpl(val apiService: ApiService) :ProductDatasource {
    override fun getProduct(): Single<List<Products>> =apiService.getProuduct()
    override fun getItemDef(): Single<List<DefItemProduct>> =apiService.getItemDef()
    override fun checkToken(token: String?, refresh_token: String?): Single<LoginMessage> = apiService.checkToken(token,refresh_token)
}
package fathi.reza.been_tech.Home.repo

import fathi.reza.been_tech.Data.DefItemProduct
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.Data.Products
import fathi.reza.been_tech.Home.datasource.ProductDatasource
import fathi.reza.been_tech.Home.datasource.RemoteProductsDsImpl
import io.reactivex.Single

class ProductRepoImpl(private val remoteProductsDsImpl: ProductDatasource) :ProductRepo {
    override fun getProduct(): Single<List<Products>> =remoteProductsDsImpl.getProduct()
    override fun getItemDef(): Single<List<DefItemProduct>> = remoteProductsDsImpl.getItemDef()
    override fun checkToken(token: String?, refresh_token: String?): Single<LoginMessage> =remoteProductsDsImpl.checkToken(token,refresh_token)
}
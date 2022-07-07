package fathi.reza.been_tech.Home.datasource

import fathi.reza.been_tech.Data.DefItemProduct
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.Data.Products
import io.reactivex.Single

interface ProductDatasource {
    fun getProduct():Single<List<Products>>
    fun getItemDef():Single<List<DefItemProduct>>
    fun checkToken(token:String?,refresh_token:String?):Single<LoginMessage>
}
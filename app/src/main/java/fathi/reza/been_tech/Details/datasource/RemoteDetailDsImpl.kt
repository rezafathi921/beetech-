package fathi.reza.been_tech.Details.datasource

import fathi.reza.been_tech.Data.Detail.Detail
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.Retrofit.ApiService
import io.reactivex.Single

class RemoteDetailDsImpl(val apiService: ApiService):DetailDataSource{
    override fun getDetail(id:String, user: String): Single<Detail> =apiService.getDetail(id,user)
    override fun addToBascket(productId: String): Single<LoginMessage> = apiService.addToBasket(productId)
}


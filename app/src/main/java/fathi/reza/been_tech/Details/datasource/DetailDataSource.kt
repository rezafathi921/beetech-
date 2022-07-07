package fathi.reza.been_tech.Details.datasource

import fathi.reza.been_tech.Data.Detail.Detail
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import io.reactivex.Single

interface DetailDataSource {
    fun getDetail(id:String, user: String): Single<Detail>
    fun addToBascket(productId:String):Single<LoginMessage>
}
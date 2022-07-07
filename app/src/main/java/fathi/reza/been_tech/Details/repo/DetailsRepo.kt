package fathi.reza.been_tech.Details.repo

import fathi.reza.been_tech.Data.Detail.Detail
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import io.reactivex.Single

interface DetailsRepo {
    fun getDetail(id:String, user: String):Single<Detail>
    fun addToBascket(productId:String):Single<LoginMessage>
}
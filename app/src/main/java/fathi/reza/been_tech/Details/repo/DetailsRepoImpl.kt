package fathi.reza.been_tech.Details.repo

import fathi.reza.been_tech.Data.Detail.Detail
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.Details.datasource.DetailDataSource
import io.reactivex.Single

class DetailsRepoImpl(val remoteDetailDsImple: DetailDataSource) : DetailsRepo {
    override fun getDetail(id:String, user: String): Single<Detail> = remoteDetailDsImple.getDetail(id,user)
    override fun addToBascket(productId: String): Single<LoginMessage> =remoteDetailDsImple.addToBascket(productId)

}

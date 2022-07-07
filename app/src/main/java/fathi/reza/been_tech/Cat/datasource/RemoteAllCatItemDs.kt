package fathi.reza.been_tech.Cat.datasource

import fathi.reza.been_tech.Data.CatFragment.AllCat
import fathi.reza.been_tech.Retrofit.ApiService
import io.reactivex.Single

class RemoteAllCatItemDs(val apiService: ApiService):IAllCatItemDs {
    override fun getAllCatItem(): Single<AllCat> = apiService.getallcatitem()
}
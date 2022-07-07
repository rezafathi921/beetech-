package fathi.reza.been_tech.Home.datasource

import fathi.reza.been_tech.Data.Cats
import fathi.reza.been_tech.Retrofit.ApiService
import io.reactivex.Single

class RemoteCatDsImpl(val apiService: ApiService):CatDataSource {
    override fun getCats(): Single<List<Cats>> = apiService.getCat()
}
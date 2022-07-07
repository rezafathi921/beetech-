package fathi.reza.been_tech.Home.repo

import fathi.reza.been_tech.Data.Cats
import fathi.reza.been_tech.Home.datasource.CatDataSource
import fathi.reza.been_tech.Home.datasource.RemoteCatDsImpl
import io.reactivex.Single

class CatRepImpl(val remoteCatDsImpl: CatDataSource) :CatRepo {
    override fun getCats(): Single<List<Cats>> =remoteCatDsImpl.getCats()
}
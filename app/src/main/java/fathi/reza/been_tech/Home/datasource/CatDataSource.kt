package fathi.reza.been_tech.Home.datasource

import fathi.reza.been_tech.Data.Cats
import io.reactivex.Single

interface CatDataSource {
    fun getCats():Single<List<Cats>>
}
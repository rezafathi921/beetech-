package fathi.reza.been_tech.Home.repo

import fathi.reza.been_tech.Data.Cats
import io.reactivex.Single

interface CatRepo {

    fun getCats() :Single<List<Cats>>

}
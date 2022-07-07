package fathi.reza.been_tech.Cat.datasource

import fathi.reza.been_tech.Data.CatFragment.AllCat
import io.reactivex.Single

interface IAllCatItemDs {
    fun getAllCatItem():Single<AllCat>
}
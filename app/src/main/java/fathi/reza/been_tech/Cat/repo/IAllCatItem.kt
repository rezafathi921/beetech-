package fathi.reza.been_tech.Cat.repo

import fathi.reza.been_tech.Data.CatFragment.AllCat
import io.reactivex.Single

interface IAllCatItem {
    fun getAllCatItem():Single<AllCat>
}
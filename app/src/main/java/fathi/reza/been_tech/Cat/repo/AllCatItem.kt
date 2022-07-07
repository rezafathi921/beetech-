package fathi.reza.been_tech.Cat.repo

import fathi.reza.been_tech.Cat.datasource.IAllCatItemDs
import fathi.reza.been_tech.Data.CatFragment.AllCat
import io.reactivex.Single

class AllCatItem(val remoteAllCatItemDs:IAllCatItemDs):IAllCatItem {
    override fun getAllCatItem(): Single<AllCat> = remoteAllCatItemDs.getAllCatItem()
}
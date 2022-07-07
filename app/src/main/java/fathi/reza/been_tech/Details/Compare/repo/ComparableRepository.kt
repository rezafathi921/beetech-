package fathi.reza.been_tech.Details.Compare.repo

import fathi.reza.been_tech.Data.Detail.CompareList
import io.reactivex.Single

interface ComparableRepository {
    fun getCompareDataSource(cat:String): Single<List<CompareList>>
}
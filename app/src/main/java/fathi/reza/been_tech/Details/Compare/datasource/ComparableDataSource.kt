package fathi.reza.been_tech.Details.Compare.datasource

import fathi.reza.been_tech.Data.Detail.CompareList
import io.reactivex.Single

interface ComparableDataSource {
    fun getCompareDataSource(cat:String):Single<List<CompareList>>
}
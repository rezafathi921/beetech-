package fathi.reza.been_tech.Details.Compare.repo

import fathi.reza.been_tech.Data.Detail.CompareList
import fathi.reza.been_tech.Details.Compare.datasource.ComparableDataSource
import io.reactivex.Single

class ComparableRepositoryImpl(val comparableDataSource: ComparableDataSource):ComparableRepository {
    override fun getCompareDataSource(cat: String): Single<List<CompareList>> =comparableDataSource.getCompareDataSource(cat)
}
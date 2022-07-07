package fathi.reza.been_tech.Details.Compare.datasource

import fathi.reza.been_tech.Data.Detail.CompareList
import fathi.reza.been_tech.Retrofit.ApiService
import io.reactivex.Single

class RemoteComparableDataSourceImpl(val apiService: ApiService) :ComparableDataSource {
    override fun getCompareDataSource(cat: String): Single<List<CompareList>> =apiService.getcomparelist(cat)
}
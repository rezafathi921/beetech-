package fathi.reza.been_tech.Details.Chart.datasource

import fathi.reza.been_tech.Data.Detail.PriceHistory
import fathi.reza.been_tech.Retrofit.ApiService
import io.reactivex.Single

class RemotePriceHistoryDataSorce(val apiService: ApiService):IPriceHistoryDataSource {
    override fun getPriceHistory(id:String):Single<List<PriceHistory>> = apiService.getPriceHistory(id)
}
package fathi.reza.been_tech.Details.Chart.datasource

import fathi.reza.been_tech.Data.Detail.PriceHistory
import io.reactivex.Single

interface IPriceHistoryDataSource {
    fun getPriceHistory(id:String) : Single<List<PriceHistory>>
}
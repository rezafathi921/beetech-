package fathi.reza.been_tech.Details.Chart.repo


import fathi.reza.been_tech.Data.Detail.PriceHistory
import fathi.reza.been_tech.Details.Chart.datasource.IPriceHistoryDataSource
import io.reactivex.Single

class PriceHistoryRepo(val remotePriceHistoryDataSorce:IPriceHistoryDataSource):IPriceHistoryRepo {
    override fun getPriceHistory(id: String): Single<List<PriceHistory>> = remotePriceHistoryDataSorce.getPriceHistory(id)
}
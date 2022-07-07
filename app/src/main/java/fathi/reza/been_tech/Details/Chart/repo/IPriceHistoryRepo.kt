package fathi.reza.been_tech.Details.Chart.repo


import fathi.reza.been_tech.Data.Detail.PriceHistory
import io.reactivex.Single

interface IPriceHistoryRepo {
    fun getPriceHistory(id:String):Single<List<PriceHistory>>
}
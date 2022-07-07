package fathi.reza.been_tech.Details.Chart.viewmodel

import androidx.lifecycle.MutableLiveData
import fathi.reza.been_tech.Data.Detail.PriceHistory
import fathi.reza.been_tech.Details.Chart.repo.IPriceHistoryRepo
import fathi.reza.been_tech.Utills.BaseViewModel
import fathi.reza.been_tech.Utills.MySingleObserver
import fathi.reza.been_tech.Utills.singleHelper

class PriceHistoryViewModel(val id :String,val iPriceHistoryRepo: IPriceHistoryRepo):BaseViewModel() {
val chartData=MutableLiveData<List<PriceHistory>>()

    init {
        progressBarLiveData.value=true
        iPriceHistoryRepo.getPriceHistory(id)
            .singleHelper()
            .doFinally {
                progressBarLiveData.value=false
            }
            .subscribe(object : MySingleObserver<List<PriceHistory>>(compositedisposable){
                override fun onSuccess(t: List<PriceHistory>) {
                        chartData.value=t
                }

            })
    }
}
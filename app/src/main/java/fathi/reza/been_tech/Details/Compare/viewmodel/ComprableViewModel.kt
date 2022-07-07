package fathi.reza.been_tech.Details.Compare.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import fathi.reza.been_tech.Data.Detail.CompareList
import fathi.reza.been_tech.Details.Compare.repo.ComparableRepository
import fathi.reza.been_tech.Utills.BaseViewModel
import fathi.reza.been_tech.Utills.DATA
import fathi.reza.been_tech.Utills.MySingleObserver
import fathi.reza.been_tech.Utills.singleHelper


class ComprableViewModel(bundle:Bundle,comparableRepository: ComparableRepository):BaseViewModel() {

    val compareListLiveData= MutableLiveData<List<CompareList>>()
    init {
        val productCat=bundle.getString(DATA)
        progressBarLiveData.value=true

        if (productCat != null) {
            comparableRepository.getCompareDataSource(productCat)
                .singleHelper()
                .doFinally { progressBarLiveData.value=false }
                .subscribe(object :MySingleObserver<List<CompareList>>(compositedisposable){
                    override fun onSuccess(t: List<CompareList>) {
                        compareListLiveData.value=t
                    }

                })
        }
    }
}
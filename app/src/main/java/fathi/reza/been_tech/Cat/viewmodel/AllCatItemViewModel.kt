package fathi.reza.been_tech.Cat.viewmodel

import androidx.lifecycle.MutableLiveData
import fathi.reza.been_tech.Cat.repo.IAllCatItem
import fathi.reza.been_tech.Data.CatFragment.AllCat
import fathi.reza.been_tech.Utills.BaseViewModel
import fathi.reza.been_tech.Utills.MySingleObserver
import fathi.reza.been_tech.Utills.singleHelper

class AllCatItemViewModel(iAllCatItem: IAllCatItem): BaseViewModel() {

    val allCatItemLiveData = MutableLiveData<AllCat>()

    init {
        progressBarLiveData.value=true
        iAllCatItem.getAllCatItem()
            .doFinally { progressBarLiveData.postValue(false) }
            .singleHelper()
            .subscribe(object : MySingleObserver<AllCat>(compositedisposable){
                override fun onSuccess(t: AllCat) {
                    allCatItemLiveData.value=t
                }


            })


    }
}



package fathi.reza.been_tech.Details.Compare.viewmodel


import androidx.lifecycle.MutableLiveData
import fathi.reza.been_tech.Data.Detail.Detail
import fathi.reza.been_tech.Data.Detail.ParentAndChild
import fathi.reza.been_tech.Details.repo.DetailsRepo
import fathi.reza.been_tech.Utills.BaseViewModel
import fathi.reza.been_tech.Utills.MySingleObserver
import fathi.reza.been_tech.Utills.PersianToEnglish
import fathi.reza.been_tech.Utills.singleHelper

class CompareViewModel(id:String,detailsRepo: DetailsRepo): BaseViewModel() {

    val secondPropertyProduct=MutableLiveData<List<ParentAndChild>>()
    val secondPropertyProductCommon=MutableLiveData<List<String>>()





    init {

        progressBarLiveData.value=true
        detailsRepo.getDetail(id,0.toString())
            .singleHelper()
            .doFinally { progressBarLiveData.value=false }
            .subscribe(object :MySingleObserver<Detail>(compositedisposable){
            override fun onSuccess(t: Detail) {
                val arrayListCommonProperty = mutableListOf<String>()
                val price= PersianToEnglish(((((100 - (t.discount.toInt())) * ((t.price).toInt())) / 100).toString())) + "تومان"
                arrayListCommonProperty.add(price)
                arrayListCommonProperty.add(t.pic)
                arrayListCommonProperty.add(t.title)
                secondPropertyProductCommon.value=arrayListCommonProperty
                secondPropertyProduct.value=t.parent_and_child
            }

        })

    }
}
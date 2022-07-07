package fathi.reza.been_tech.Details.viewModel

import androidx.lifecycle.MutableLiveData
import fathi.reza.been_tech.Data.Detail.Detail
import fathi.reza.been_tech.Data.Detail.Favorit
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.Details.repo.DetailsRepo
import fathi.reza.been_tech.LoginAndRegister.TokenHolder
import fathi.reza.been_tech.LoginAndRegister.repository.ILoginRepo
import fathi.reza.been_tech.Utills.BaseViewModel
import fathi.reza.been_tech.Utills.MySingleObserver
import fathi.reza.been_tech.Utills.singleHelper
import io.reactivex.Single

class DetailViewModel(val id:String,val detailsRepo: DetailsRepo,val loginRepository:ILoginRepo) : BaseViewModel() {
    val detailLiveData= MutableLiveData<Detail>()
    val idIntentLiveData=MutableLiveData<String>()
    val favArrayInDetailLiveData= MutableLiveData<List<Favorit>>()
    init {
        idIntentLiveData.value=id
        progressBarLiveData.value=true
        loginRepository.loadToken()
        if (TokenHolder.token==""){
            detailsRepo.getDetail(idIntentLiveData.value!!,0.toString())
                .singleHelper()
                .doFinally { progressBarLiveData.value=false }
                .subscribe(object:MySingleObserver<Detail>(compositedisposable){
                    override fun onSuccess(t: Detail) {
                        detailLiveData.value=t
                        favArrayInDetailLiveData.value=t.favorit_for_user
                    }
                })
        }else{

            detailsRepo.getDetail(idIntentLiveData.value!!,TokenHolder.token!!)
                .singleHelper()
                .doFinally { progressBarLiveData.value=false }
                .subscribe(object:MySingleObserver<Detail>(compositedisposable){
                    override fun onSuccess(t: Detail) {
                        detailLiveData.value=t
                        favArrayInDetailLiveData.value=t.favorit_for_user
                    }
                })

        }

    }
    fun addToBasket(id: String):Single<LoginMessage>{
      return  detailsRepo.addToBascket(id)
    }
}
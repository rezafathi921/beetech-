package fathi.reza.been_tech.LoginAndRegister.viewModel

import androidx.lifecycle.MutableLiveData
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.LoginAndRegister.repository.ILoginRepo
import fathi.reza.been_tech.LoginAndRegister.repository.LoginRepo
import fathi.reza.been_tech.Utills.BaseViewModel
import fathi.reza.been_tech.Utills.MySingleObserver
import fathi.reza.been_tech.Utills.singleHelper
import io.reactivex.Single

class LoginViewModel(private val loginRepo: ILoginRepo) :BaseViewModel() {
    val loginMessage = MutableLiveData<LoginMessage>()
    val registerMessage = MutableLiveData<LoginMessage>()
    var checkLoginLivedataStatus= MutableLiveData<Boolean>()
    val statusAddOrDeleteToFav= MutableLiveData<LoginMessage>()


    fun login(email: String, pass: String){
        progressBarLiveData.value = true
         loginRepo.login(email,pass)
            .singleHelper()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : MySingleObserver<LoginMessage>(compositedisposable) {
                override fun onSuccess(t: LoginMessage) {
                    loginMessage.value=t
                }
            })
    }
    fun register(email: String, pass: String) {
        progressBarLiveData.value = true
         loginRepo.register(email, pass)
            .singleHelper()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : MySingleObserver<LoginMessage>(compositedisposable) {
                override fun onSuccess(t: LoginMessage) {
                    registerMessage.value=t
                }
            })
    }
   fun checkLogin(){
       checkLoginLivedataStatus.value=loginRepo.checkLogin()
   }

    fun addToFav(id:String){
        progressBarLiveData.value=true
        loginRepo.addToFav(id)
            .singleHelper()
            .doFinally { progressBarLiveData.postValue(false) }
            .subscribe(object :MySingleObserver<LoginMessage>(compositedisposable){
                override fun onSuccess(t: LoginMessage) {
                    statusAddOrDeleteToFav.value=t
                }

            })
    }
    fun deleteFromFav(id:String){
        progressBarLiveData.value=true
        loginRepo.deleteFromFav(id)
            .singleHelper()
            .doFinally { progressBarLiveData.postValue(false) }
            .subscribe(object :MySingleObserver<LoginMessage>(compositedisposable){
                override fun onSuccess(t: LoginMessage) {
                    statusAddOrDeleteToFav.value=t
                }

            })
    }



}
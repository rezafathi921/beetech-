package fathi.reza.been_tech.CheckOut.viewmodel

import androidx.lifecycle.MutableLiveData
import fathi.reza.been_tech.CheckOut.repository.ICheckout
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.Utills.BaseViewModel
import fathi.reza.been_tech.Utills.MySingleObserver
import fathi.reza.been_tech.Utills.singleHelper

class CheckOutViewModel(iCheckout: ICheckout,orderId:String) : BaseViewModel() {
          val checkoutLiveData = MutableLiveData<LoginMessage>()
     init {
          progressBarLiveData.value=true
          iCheckout.checkout(orderId)
               .doFinally { progressBarLiveData.value=false }
               .singleHelper()
               .subscribe(object: MySingleObserver<LoginMessage>(compositedisposable){
                    override fun onSuccess(t: LoginMessage) {
                         checkoutLiveData.value=t
                    }
               })

     }
}
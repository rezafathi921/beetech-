package fathi.reza.been_tech


import fathi.reza.been_tech.Cart.repo.ICartItemRpo
import fathi.reza.been_tech.Data.CartItemCount
import fathi.reza.been_tech.LoginAndRegister.TokenHolder
import fathi.reza.been_tech.Utills.BaseViewModel
import fathi.reza.been_tech.Utills.MySingleObserver
import fathi.reza.been_tech.Utills.singleHelper
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(val repository:ICartItemRpo):BaseViewModel() {
    fun getCartItemCount(){
        if (!TokenHolder.token.isNullOrEmpty()){
            repository.getCartItemCount()
                .singleHelper()
            .subscribe(object : MySingleObserver<CartItemCount>(compositedisposable) {
                override fun onSuccess(t: CartItemCount) {
                    EventBus.getDefault().postSticky(t)
                }
            })
        }


    }
}
package fathi.reza.been_tech.Home.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import fathi.reza.been_tech.Data.*
import fathi.reza.been_tech.Data.LoginAndRegister.LoginMessage
import fathi.reza.been_tech.Home.repo.BannerRep
import fathi.reza.been_tech.Home.repo.CatRepo
import fathi.reza.been_tech.Home.repo.ProductRepo
import fathi.reza.been_tech.LoginAndRegister.TokenHolder
import fathi.reza.been_tech.Utills.BaseViewModel
import fathi.reza.been_tech.Utills.MySingleObserver
import fathi.reza.been_tech.Utills.singleHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject

class HomeViewModel(private val bannerRepository: BannerRep, private val catRepo: CatRepo, private val productRepo: ProductRepo,val sharedPreferences: SharedPreferences) : BaseViewModel() {

    val bannerLiveData = MutableLiveData<List<Banners>>()
    val catLiveData = MutableLiveData<List<Cats>>()
    val productLiveData=MutableLiveData<List<Products>>()
    val defItemProductLiveData= MutableLiveData<List<DefItemProduct>>()
    val checkTokenLiveData= MutableLiveData<LoginMessage>()


    init {
        progressBarLiveData.value = true
        bannerRepository.getBanners()
            .singleHelper()
            .subscribe(object : MySingleObserver<List<Banners>>(compositedisposable) {
                override fun onSuccess(t: List<Banners>) {
                    bannerLiveData.value = t
                }

            })

        catRepo.getCats()
            .singleHelper()
            .subscribe(object : MySingleObserver<List<Cats>>(compositedisposable) {
                override fun onSuccess(t: List<Cats>) {
                    catLiveData.value = t
                }

            })
           productRepo.getProduct()
               .singleHelper()
               .doFinally { progressBarLiveData.value = false }
               .subscribe(object : MySingleObserver<List<Products>>(compositedisposable){
                   override fun onSuccess(t: List<Products>) {
                       productLiveData.value=t
                   }

               })

            productRepo.checkToken(TokenHolder.token,TokenHolder.refresh_token)
                .singleHelper()
                .subscribe(object :MySingleObserver<LoginMessage>(compositedisposable){
                override fun onSuccess(t: LoginMessage) {
                    checkTokenLiveData.value=t
                }

            })





        productRepo.getItemDef()
            .singleHelper()
            .subscribe(object : MySingleObserver<List<DefItemProduct>>(compositedisposable){
                override fun onSuccess(t: List<DefItemProduct>) {
                    defItemProductLiveData.value=t
                }

            })


    }
}
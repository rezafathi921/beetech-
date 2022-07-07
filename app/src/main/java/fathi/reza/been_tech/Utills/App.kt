package fathi.reza.been_tech.Utills

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import com.facebook.drawee.backends.pipeline.Fresco
import fathi.reza.been_tech.Cart.adapter.CartAdapter
import fathi.reza.been_tech.Cart.dataSource.RemoteCartItemDS
import fathi.reza.been_tech.Cart.repo.CarItemRpo
import fathi.reza.been_tech.Cart.repo.ICartItemRpo
import fathi.reza.been_tech.Cart.viewModel.CartItemViewModel
import fathi.reza.been_tech.Cat.adapter.AllCatItemAdapter
import fathi.reza.been_tech.Cat.datasource.RemoteAllCatItemDs
import fathi.reza.been_tech.Cat.repo.AllCatItem
import fathi.reza.been_tech.Cat.repo.IAllCatItem
import fathi.reza.been_tech.Cat.viewmodel.AllCatItemViewModel
import fathi.reza.been_tech.CheckOut.datasource.RemoteCheckOut
import fathi.reza.been_tech.CheckOut.repository.CheckOutImpl
import fathi.reza.been_tech.CheckOut.repository.ICheckout
import fathi.reza.been_tech.CheckOut.viewmodel.CheckOutViewModel
import fathi.reza.been_tech.Data.Cart.CartResponse
import fathi.reza.been_tech.Data.Cats
import fathi.reza.been_tech.Data.DefItemProduct
import fathi.reza.been_tech.Data.Detail.RatingItem
import fathi.reza.been_tech.Data.Products
import fathi.reza.been_tech.Details.Chart.datasource.RemotePriceHistoryDataSorce
import fathi.reza.been_tech.Details.Chart.repo.IPriceHistoryRepo
import fathi.reza.been_tech.Details.Chart.repo.PriceHistoryRepo
import fathi.reza.been_tech.Details.Chart.viewmodel.PriceHistoryViewModel
import fathi.reza.been_tech.Details.Compare.adapter.CompareAdapter
import fathi.reza.been_tech.Details.Compare.adapter.CompareListAdapter
import fathi.reza.been_tech.Details.Compare.datasource.RemoteComparableDataSourceImpl
import fathi.reza.been_tech.Details.Compare.repo.ComparableRepository
import fathi.reza.been_tech.Details.Compare.repo.ComparableRepositoryImpl
import fathi.reza.been_tech.Details.Compare.viewmodel.CompareViewModel
import fathi.reza.been_tech.Details.Compare.viewmodel.ComprableViewModel
import fathi.reza.been_tech.Details.adapter.ProductRatingItemAdapter
import fathi.reza.been_tech.Details.adapter.PropertyAdapter
import fathi.reza.been_tech.Details.datasource.RemoteDetailDsImpl
import fathi.reza.been_tech.Details.repo.DetailsRepo
import fathi.reza.been_tech.Details.repo.DetailsRepoImpl
import fathi.reza.been_tech.Details.viewModel.DetailViewModel
import fathi.reza.been_tech.Home.*
import fathi.reza.been_tech.Home.adapter.CatAdapter
import fathi.reza.been_tech.Home.adapter.ProductsAdapter
import fathi.reza.been_tech.Home.datasource.LocalBannerDsImpl
import fathi.reza.been_tech.Home.datasource.RemoteBannerDsImpl
import fathi.reza.been_tech.Home.datasource.RemoteCatDsImpl
import fathi.reza.been_tech.Home.datasource.RemoteProductsDsImpl
import fathi.reza.been_tech.Home.repo.*
import fathi.reza.been_tech.Home.viewModel.HomeViewModel
import fathi.reza.been_tech.LoginAndRegister.dataSource.LocalLoginDs
import fathi.reza.been_tech.LoginAndRegister.dataSource.RemoteLoginDs
import fathi.reza.been_tech.LoginAndRegister.repository.ILoginRepo
import fathi.reza.been_tech.LoginAndRegister.repository.LoginRepo
import fathi.reza.been_tech.LoginAndRegister.viewModel.LoginViewModel
import fathi.reza.been_tech.MainViewModel
import fathi.reza.been_tech.Retrofit.getclient
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.ArrayList

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)


        val Mymodules = module {

            single<ImageLoading> { ImageLoadingImpl() }
            single { getclient() }
            factory<CatRepo> { CatRepImpl(RemoteCatDsImpl(get())) }
            factory<ProductRepo> { ProductRepoImpl(RemoteProductsDsImpl(get())) }
            factory<BannerRep> { BannerRepImpl(LocalBannerDsImpl(), RemoteBannerDsImpl(get())) }
            viewModel { HomeViewModel(get(),get(),get(),get()) }
            factory { (cats:List<Cats>)->CatAdapter(cats,get()) }
            factory { (products:List<Products>,defItemProduct:List<DefItemProduct>)->ProductsAdapter(products,get(),defItemProduct) }
            factory { (final_array:ArrayList<Any>)->PropertyAdapter(final_array) }
            factory { (ratingItems:ArrayList<RatingItem>)-> ProductRatingItemAdapter(ratingItems) }
            factory<DetailsRepo> {DetailsRepoImpl(RemoteDetailDsImpl(get()))}
            viewModel { (id:String)->DetailViewModel(id,get(),get()) }
            factory<IPriceHistoryRepo> { PriceHistoryRepo(RemotePriceHistoryDataSorce(get())) }
            viewModel { (id:String)->PriceHistoryViewModel(id,get()) }
            factory { CompareListAdapter(get()) }
            factory<ComparableRepository> { ComparableRepositoryImpl(RemoteComparableDataSourceImpl(get())) }
            viewModel {(bundle:Bundle)-> ComprableViewModel(bundle,get()) }
            viewModel {(seondProductId:String)->CompareViewModel(seondProductId,get()) }
            factory { CompareAdapter() }
            single<SharedPreferences> { this@App.getSharedPreferences("data-user", MODE_PRIVATE) }
            factory<ILoginRepo> { LoginRepo(RemoteLoginDs(get()),LocalLoginDs(get())) }
            viewModel { LoginViewModel(get()) }
            factory { AllCatItemAdapter(get()) }
            factory<IAllCatItem> { AllCatItem(RemoteAllCatItemDs(get())) }
            viewModel { AllCatItemViewModel(get()) }
            factory {(cartResponse:CartResponse)->CartAdapter(cartResponse,get()) }
            factory<ICartItemRpo> { CarItemRpo(RemoteCartItemDS(get())) }
            viewModel { CartItemViewModel(get()) }
            viewModel { MainViewModel(get()) }
            factory<ICheckout>{CheckOutImpl(RemoteCheckOut(get()))}
            viewModel { (orderId:String)->CheckOutViewModel(get(),orderId) }

        }

        startKoin {
            androidContext(this@App)
            modules(Mymodules)
        }

        val loginRepo:ILoginRepo=get()
        loginRepo.loadToken()
    }


}
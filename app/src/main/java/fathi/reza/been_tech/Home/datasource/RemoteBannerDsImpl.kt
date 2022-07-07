package fathi.reza.been_tech.Home.datasource

import fathi.reza.been_tech.Data.Banners
import fathi.reza.been_tech.Home.datasource.BannerDataSource
import fathi.reza.been_tech.Retrofit.ApiService
import io.reactivex.Single

class RemoteBannerDsImpl(val apiService: ApiService): BannerDataSource {
    override fun getBanners(): Single<List<Banners>> = apiService.getBannerSliders()

}
package fathi.reza.been_tech.Home.datasource

import fathi.reza.been_tech.Data.Banners
import io.reactivex.Single

interface BannerDataSource {

        fun getBanners(): Single<List<Banners>>
}
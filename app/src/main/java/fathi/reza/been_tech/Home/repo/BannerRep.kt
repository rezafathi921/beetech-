package fathi.reza.been_tech.Home.repo

import fathi.reza.been_tech.Data.Banners
import io.reactivex.Single

interface BannerRep {

    fun getBanners():Single<List<Banners>>
}
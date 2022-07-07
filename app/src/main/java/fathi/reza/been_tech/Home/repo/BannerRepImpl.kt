package fathi.reza.been_tech.Home.repo

import fathi.reza.been_tech.Data.Banners
import fathi.reza.been_tech.Home.datasource.BannerDataSource
import fathi.reza.been_tech.Home.repo.BannerRep
import io.reactivex.Single

class BannerRepImpl(
    val localBannerDsImpl: BannerDataSource,
    val remoteBannerDsImpl: BannerDataSource
): BannerRep {
    override fun getBanners(): Single<List<Banners>> =remoteBannerDsImpl.getBanners()
}
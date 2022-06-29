package com.hosein.nzd.nikestore.data.repository

import com.hosein.nzd.nikestore.data.Banner
import com.hosein.nzd.nikestore.data.repository.BannerRepository
import com.hosein.nzd.nikestore.data.repository.source.BannerRemoteDataSource
import io.reactivex.rxjava3.core.Single

class BannerRepositoryImpl(val bannerRemoteDataSource: BannerRemoteDataSource) : BannerRepository {
    override fun getBanners(): Single<List<Banner>> = bannerRemoteDataSource.getBanners()
}
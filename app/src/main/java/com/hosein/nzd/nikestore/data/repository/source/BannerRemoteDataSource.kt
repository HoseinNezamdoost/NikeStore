package com.hosein.nzd.nikestore.data.repository.source

import com.hosein.nzd.nikestore.data.Banner
import com.hosein.nzd.nikestore.services.http.ApiService
import io.reactivex.rxjava3.core.Single

class BannerRemoteDataSource(val apiService: ApiService) :BannerDataSource{
    override fun getBanners(): Single<List<Banner>> = apiService.getBanners()
}
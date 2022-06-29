package com.hosein.nzd.nikestore.data.repository.source

import com.hosein.nzd.nikestore.data.Banner
import io.reactivex.rxjava3.core.Single

interface BannerDataSource {
    fun getBanners(): Single<List<Banner>>
}
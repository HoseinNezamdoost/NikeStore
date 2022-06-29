package com.hosein.nzd.nikestore.data.repository

import com.hosein.nzd.nikestore.data.Banner
import io.reactivex.rxjava3.core.Single

interface BannerRepository {
    fun getBanners():Single<List<Banner>>
}
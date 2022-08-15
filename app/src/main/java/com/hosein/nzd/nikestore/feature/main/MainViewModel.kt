package com.hosein.nzd.nikestore.feature.main

import androidx.lifecycle.MutableLiveData
import com.hosein.nzd.nikestore.common.NikeMainGetProduct
import com.hosein.nzd.nikestore.common.NikeSingleObservable
import com.hosein.nzd.nikestore.common.NikeViewModel
import com.hosein.nzd.nikestore.data.*
import com.hosein.nzd.nikestore.data.repository.BannerRepository
import com.hosein.nzd.nikestore.data.repository.ProductRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleOnSubscribe
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(productRepository: ProductRepository, bannerRepository: BannerRepository) :
    NikeViewModel() {

    var productLiveDataLast = MutableLiveData<List<Product>>()
    var productLiveDataPopular = MutableLiveData<List<Product>>()
    var bannerLiveData = MutableLiveData<List<Banner>>()

    init {

        progressBraLiveData.value = true
        productLiveDataLast = NikeMainGetProduct(productRepository , SORT_LAST).getProductWithSort()
        productLiveDataPopular = NikeMainGetProduct(productRepository , SORT_POPULAR).getProductWithSort()

        bannerRepository.getBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBraLiveData.value = false }
            .subscribe(object : NikeSingleObservable<List<Banner>>(disposable) {
                override fun onSuccess(t: List<Banner>) {
                    bannerLiveData.value = t
                }
            })

    }
}
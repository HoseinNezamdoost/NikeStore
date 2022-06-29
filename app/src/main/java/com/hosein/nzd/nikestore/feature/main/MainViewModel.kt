package com.hosein.nzd.nikestore.feature.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hosein.nzd.nikestore.common.NikeSingleObservable
import com.hosein.nzd.nikestore.common.NikeViewModel
import com.hosein.nzd.nikestore.data.Banner
import com.hosein.nzd.nikestore.data.Product
import com.hosein.nzd.nikestore.data.repository.BannerRepository
import com.hosein.nzd.nikestore.data.repository.ProductRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.core.SingleOnSubscribe
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(productRepository: ProductRepository, bannerRepository: BannerRepository) :
    NikeViewModel() {

    var productLiveData = MutableLiveData<List<Product>>()
    var bannerLiveData = MutableLiveData<List<Banner>>()

    init {
        progressBraLiveData.value = true
        productRepository.getProduct()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBraLiveData.value = false }
            .subscribe(object : NikeSingleObservable<List<Product>>(disposable) {
                override fun onSuccess(t: List<Product>) {
                    productLiveData.value = t
                }
            })

        bannerRepository.getBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObservable<List<Banner>>(disposable) {
                override fun onSuccess(t: List<Banner>) {
                    bannerLiveData.value = t
                }
            })

    }
}
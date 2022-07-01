package com.hosein.nzd.nikestore.common

import androidx.lifecycle.MutableLiveData
import com.hosein.nzd.nikestore.common.NikeViewModel.Companion.disposable
import com.hosein.nzd.nikestore.common.NikeViewModel.Companion.progressBraLiveData
import com.hosein.nzd.nikestore.data.Product
import com.hosein.nzd.nikestore.data.repository.ProductRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class NikeMainGetProduct(val repository: ProductRepository, val sort: Int) {

    var productLive = MutableLiveData<List<Product>>()

    fun getProductWithSort() : MutableLiveData<List<Product>>{
        repository.getProduct(sort)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally{progressBraLiveData.value = false}
            .subscribe(object : NikeSingleObservable<List<Product>>(disposable) {
                override fun onSuccess(t: List<Product>) {
                    productLive.value = t
                }
            })
        return productLive
    }

}
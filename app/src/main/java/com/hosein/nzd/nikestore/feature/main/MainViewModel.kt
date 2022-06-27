package com.hosein.nzd.nikestore.feature.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hosein.nzd.nikestore.common.NikeViewModel
import com.hosein.nzd.nikestore.data.Product
import com.hosein.nzd.nikestore.data.repository.ProductRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.core.SingleOnSubscribe
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val productRepository: ProductRepository):NikeViewModel() {

    var productLiveData = MutableLiveData<List<Product>>()

    init {
        productRepository.getProduct()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Product>>{
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onSuccess(t: List<Product>) {
                    productLiveData.value = t
                }

                override fun onError(e: Throwable) {
                    Log.i("error", "onError: $e")
                }
            })
    }
}
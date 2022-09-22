package com.hosein.nzd.nikestore.feature.profile.favorite

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hosein.nzd.nikestore.common.NikeCompletableObservable
import com.hosein.nzd.nikestore.common.NikeSingleObservable
import com.hosein.nzd.nikestore.common.NikeViewModel
import com.hosein.nzd.nikestore.data.Product
import com.hosein.nzd.nikestore.data.repository.ProductRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class FavoriteViewModel(private val productRepository: ProductRepository) : NikeViewModel() {

    val favoriteProductLiveData = MutableLiveData<List<Product>>()

    init {
        productRepository.getFavoriteProduct()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObservable<List<Product>>(disposable){
                override fun onSuccess(t: List<Product>) {
                    favoriteProductLiveData.value = t
                }
            })
    }

    fun delete(product: Product): Completable {
        return productRepository.deleteFromFavorite(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
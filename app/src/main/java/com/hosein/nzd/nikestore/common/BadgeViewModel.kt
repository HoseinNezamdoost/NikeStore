package com.hosein.nzd.nikestore.common

import androidx.lifecycle.MutableLiveData
import com.hosein.nzd.nikestore.data.CartItemCount
import com.hosein.nzd.nikestore.data.repository.CartRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class BadgeViewModel(val cartRepository: CartRepository) : NikeViewModel() {
    val badgeCountLiveData = MutableLiveData<Int>()

    fun getCartItemCount(){
        cartRepository.getCartItemsCount()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObservable<CartItemCount>(disposable){
                override fun onSuccess(t: CartItemCount) {
                    badgeCountLiveData.value = t.count
                }
            })
    }
}
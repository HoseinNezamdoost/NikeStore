package com.hosein.nzd.nikestore.feature.cart.shipping.checkout

import androidx.lifecycle.MutableLiveData
import com.hosein.nzd.nikestore.common.NikeSingleObservable
import com.hosein.nzd.nikestore.common.NikeViewModel
import com.hosein.nzd.nikestore.data.Checkout
import com.hosein.nzd.nikestore.data.repository.SubmitOrderRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CheckoutViewModel(orderId:Int , orderRepository: SubmitOrderRepository) : NikeViewModel() {

    val checkoutLiveData = MutableLiveData<Checkout>()

    init {
        orderRepository.checkout(orderId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObservable<Checkout>(disposable){
                override fun onSuccess(t: Checkout) {
                    checkoutLiveData.value = t
                }
            })
    }

}
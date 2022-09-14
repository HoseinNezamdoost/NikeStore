package com.hosein.nzd.nikestore.feature.cart.shipping

import com.hosein.nzd.nikestore.common.NikeViewModel
import com.hosein.nzd.nikestore.data.Checkout
import com.hosein.nzd.nikestore.data.SubmitOrderResult
import com.hosein.nzd.nikestore.data.repository.SubmitOrderRepository
import io.reactivex.rxjava3.core.Single

class ShippingViewModel(private val repositoryImpl: SubmitOrderRepository) : NikeViewModel() {

    fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String,
    ): Single<SubmitOrderResult> {
        return repositoryImpl.submit(firstName,
            lastName,
            postalCode,
            phoneNumber,
            address,
            paymentMethod)
    }

    fun checkout(orderId:Int):Single<Checkout>{
        return repositoryImpl.checkout(orderId)
    }

}
package com.hosein.nzd.nikestore.data.repository

import com.hosein.nzd.nikestore.data.Checkout
import com.hosein.nzd.nikestore.data.SubmitOrderResult
import io.reactivex.rxjava3.core.Single

interface SubmitOrderRepository {
    fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ):Single<SubmitOrderResult>

    fun checkout(orderId:Int) : Single<Checkout>
}
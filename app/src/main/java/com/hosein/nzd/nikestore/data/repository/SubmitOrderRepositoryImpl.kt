package com.hosein.nzd.nikestore.data.repository

import com.hosein.nzd.nikestore.data.Checkout
import com.hosein.nzd.nikestore.data.SubmitOrderResult
import com.hosein.nzd.nikestore.data.repository.source.SubmitDataSource
import io.reactivex.rxjava3.core.Single

class SubmitOrderRepositoryImpl(val submitDataSource: SubmitDataSource) : SubmitOrderRepository {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String,
    ) :Single<SubmitOrderResult>{
        return submitDataSource.submit(firstName , lastName , postalCode , phoneNumber , address , paymentMethod)
    }

    override fun checkout(orderId: Int) : Single<Checkout> {
        return submitDataSource.checkout(orderId)
    }
}
package com.hosein.nzd.nikestore.data.repository.source

import com.google.gson.JsonObject
import com.hosein.nzd.nikestore.data.Checkout
import com.hosein.nzd.nikestore.data.SubmitOrderResult
import com.hosein.nzd.nikestore.services.http.ApiService
import io.reactivex.rxjava3.core.Single

class SubmitRemoteDataSource(val apiService: ApiService) : SubmitDataSource {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String,
    ):Single<SubmitOrderResult> {
        return apiService.submit(JsonObject().apply {
            addProperty("first_name" , firstName)
            addProperty("last_name" , lastName)
            addProperty("postal_code" , postalCode)
            addProperty("mobile" , phoneNumber)
            addProperty("address" , address)
            addProperty("payment_method" , paymentMethod)
        })
    }

    override fun checkout(orderId: Int): Single<Checkout> {
        return apiService.checkout(orderId)
    }
}
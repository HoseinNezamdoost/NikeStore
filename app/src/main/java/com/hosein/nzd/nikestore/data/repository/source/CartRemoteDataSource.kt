package com.hosein.nzd.nikestore.data.repository.source

import com.google.gson.JsonObject
import com.hosein.nzd.nikestore.data.AddToCartResponse
import com.hosein.nzd.nikestore.data.CartItemCount
import com.hosein.nzd.nikestore.data.CartResponse
import com.hosein.nzd.nikestore.data.MessageResponse
import com.hosein.nzd.nikestore.services.http.ApiService
import io.reactivex.rxjava3.core.Single

class CartRemoteDataSource(val apiService: ApiService) : CartDataSource {
    override fun addToCart(productId: Int): Single<AddToCartResponse> = apiService.addToCart(
        JsonObject().apply {
            addProperty("product_id", productId)
        }
    )

    override fun get(): Single<CartResponse> {
        TODO("Not yet implemented")
    }

    override fun remove(cartItemId: Int): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
        TODO("Not yet implemented")
    }

    override fun getCartItemsCount(): Single<CartItemCount> {
        TODO("Not yet implemented")
    }
}
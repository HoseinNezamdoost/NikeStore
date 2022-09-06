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
        return apiService.getCartListItem()
    }

    override fun remove(cartItemId: Int): Single<MessageResponse> {
        return apiService.removeFromCart(JsonObject().apply {
            addProperty("cart_item_id" , cartItemId)
        })
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
        return apiService.changeCartCount(JsonObject().apply {
            addProperty("cart_item_id" , cartItemId)
            addProperty("count" , count)
        })
    }

    override fun getCartItemsCount(): Single<CartItemCount> {
        return apiService.getCartItemsCount()
    }
}
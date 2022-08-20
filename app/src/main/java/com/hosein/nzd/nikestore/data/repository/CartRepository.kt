package com.hosein.nzd.nikestore.data.repository

import com.hosein.nzd.nikestore.data.AddToCartResponse
import com.hosein.nzd.nikestore.data.CartItemCount
import com.hosein.nzd.nikestore.data.CartResponse
import com.hosein.nzd.nikestore.data.MessageResponse
import io.reactivex.rxjava3.core.Single

interface CartRepository {

    fun addToCart(productId: Int): Single<AddToCartResponse>
    fun get(): Single<CartResponse>
    fun remove(cartItemId: Int): Single<MessageResponse>
    fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse>
    fun getCartItemsCount(): Single<CartItemCount>
}
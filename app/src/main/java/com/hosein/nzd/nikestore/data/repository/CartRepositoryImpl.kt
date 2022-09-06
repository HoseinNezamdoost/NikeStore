package com.hosein.nzd.nikestore.data.repository

import com.hosein.nzd.nikestore.data.AddToCartResponse
import com.hosein.nzd.nikestore.data.CartItemCount
import com.hosein.nzd.nikestore.data.CartResponse
import com.hosein.nzd.nikestore.data.MessageResponse
import com.hosein.nzd.nikestore.data.repository.source.CartDataSource
import io.reactivex.rxjava3.core.Single

class CartRepositoryImpl(val remoteDataSource: CartDataSource) : CartRepository {
    override fun addToCart(productId: Int): Single<AddToCartResponse> =
        remoteDataSource.addToCart(productId)

    override fun get(): Single<CartResponse> = remoteDataSource.get()

    override fun remove(cartItemId: Int): Single<MessageResponse> = remoteDataSource.remove(cartItemId)

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> = remoteDataSource.changeCount(cartItemId,count)

    override fun getCartItemsCount(): Single<CartItemCount> = remoteDataSource.getCartItemsCount()
}
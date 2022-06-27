package com.hosein.nzd.nikestore.data.repository

import com.hosein.nzd.nikestore.data.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ProductRepository {

    fun getProduct(): Single<List<Product>>

    fun getFavoriteProduct(): Single<List<Product>>

    fun addToFavorite(): Completable

    fun deleteFromFavorite(): Completable

}
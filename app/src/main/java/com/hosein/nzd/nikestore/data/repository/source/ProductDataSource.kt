package com.hosein.nzd.nikestore.data.repository.source

import com.hosein.nzd.nikestore.data.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ProductDataSource {

    fun getProduct(sort : Int): Single<List<Product>>

    fun getFavoriteProduct(): Single<List<Product>>

    fun addToFavorite(product: Product): Completable

    fun deleteFromFavorite(product: Product): Completable

}
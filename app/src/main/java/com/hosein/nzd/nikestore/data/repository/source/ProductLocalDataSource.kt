package com.hosein.nzd.nikestore.data.repository.source

import com.hosein.nzd.nikestore.data.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProductLocalDataSource:ProductDataSource {

    override fun getProduct(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteProduct(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorite(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorite(): Completable {
        TODO("Not yet implemented")
    }
}
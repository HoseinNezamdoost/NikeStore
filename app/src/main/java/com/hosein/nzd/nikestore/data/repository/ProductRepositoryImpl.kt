package com.hosein.nzd.nikestore.data.repository

import com.hosein.nzd.nikestore.data.Product
import com.hosein.nzd.nikestore.data.repository.source.ProductDataSource
import com.hosein.nzd.nikestore.data.repository.source.ProductLocalDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val productLocalDataSource: ProductLocalDataSource,
) : ProductRepository {

    override fun getProduct(sort : Int): Single<List<Product>> = productDataSource.getProduct(sort)

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
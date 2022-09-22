package com.hosein.nzd.nikestore.data.repository

import com.hosein.nzd.nikestore.data.AppDatabase
import com.hosein.nzd.nikestore.data.Product
import com.hosein.nzd.nikestore.data.repository.source.ProductDataSource
import com.hosein.nzd.nikestore.data.repository.source.ProductLocalDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val productLocalDataSource: ProductLocalDataSource,
    private val appDatabase: AppDatabase
) : ProductRepository {

    override fun getProduct(sort : Int): Single<List<Product>> = productDataSource.getProduct(sort)

    override fun getFavoriteProduct(): Single<List<Product>> {
        return appDatabase.getDao().getFavoriteProduct()
    }

    override fun addToFavorite(product: Product): Completable {
        return appDatabase.getDao().addToFavorite(product)
    }

    override fun deleteFromFavorite(product: Product): Completable {
        return appDatabase.getDao().deleteFromFavorite(product)
    }
}
package com.hosein.nzd.nikestore.data.repository.source

import com.hosein.nzd.nikestore.data.Product
import com.hosein.nzd.nikestore.services.http.ApiService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProductRemoteDataSource(private val apiService: ApiService) : ProductDataSource {

    override fun getProduct(): Single<List<Product>> = apiService.getProducts()

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
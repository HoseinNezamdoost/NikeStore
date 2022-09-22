package com.hosein.nzd.nikestore.data.repository.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hosein.nzd.nikestore.data.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ProductLocalDataSource:ProductDataSource {

    override fun getProduct(sort : Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM favoriteProduct")
    override fun getFavoriteProduct(): Single<List<Product>>

    @Insert
    override fun addToFavorite(product: Product): Completable

    @Delete
    override fun deleteFromFavorite(product: Product): Completable
}
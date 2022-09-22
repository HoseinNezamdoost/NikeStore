package com.hosein.nzd.nikestore.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hosein.nzd.nikestore.data.repository.source.ProductLocalDataSource

@Database(entities = [Product::class] , version = 1 , exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getDao() : ProductLocalDataSource
}
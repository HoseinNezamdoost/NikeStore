package com.hosein.nzd.nikestore

import android.app.Application
import com.hosein.nzd.nikestore.data.repository.ProductRepository
import com.hosein.nzd.nikestore.data.repository.ProductRepositoryImpl
import com.hosein.nzd.nikestore.data.repository.source.ProductLocalDataSource
import com.hosein.nzd.nikestore.data.repository.source.ProductRemoteDataSource
import com.hosein.nzd.nikestore.feature.main.MainViewModel
import com.hosein.nzd.nikestore.services.http.createApiServiceInstance
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val myModule = module {
            single { createApiServiceInstance() }
            factory <ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()) , ProductLocalDataSource()) }
            viewModel { MainViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModule)
        }
    }

}
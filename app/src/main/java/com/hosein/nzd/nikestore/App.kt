package com.hosein.nzd.nikestore

import android.app.Application
import android.os.Bundle
import com.facebook.drawee.backends.pipeline.Fresco
import com.hosein.nzd.nikestore.data.repository.BannerRepository
import com.hosein.nzd.nikestore.data.repository.BannerRepositoryImpl
import com.hosein.nzd.nikestore.data.repository.ProductRepository
import com.hosein.nzd.nikestore.data.repository.ProductRepositoryImpl
import com.hosein.nzd.nikestore.data.repository.source.BannerRemoteDataSource
import com.hosein.nzd.nikestore.data.repository.source.ProductLocalDataSource
import com.hosein.nzd.nikestore.data.repository.source.ProductRemoteDataSource
import com.hosein.nzd.nikestore.feature.main.MainProductAdapter
import com.hosein.nzd.nikestore.feature.main.MainProductAdapterPopular
import com.hosein.nzd.nikestore.feature.main.MainViewModel
import com.hosein.nzd.nikestore.feature.productActivity.ProductActivityViewModel
import com.hosein.nzd.nikestore.services.http.createApiServiceInstance
import com.hosein.nzd.nikestore.services.loadImage.FrescoLoadImageService
import com.hosein.nzd.nikestore.services.loadImage.LoadImageService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)

        val myModule = module {
            single { createApiServiceInstance() }
            single <LoadImageService>{ FrescoLoadImageService() }
            factory <ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()) , ProductLocalDataSource()) }
            factory <BannerRepository>{ BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory { MainProductAdapter(get()) }
            factory { MainProductAdapterPopular(get()) }
            viewModel { MainViewModel(get() , get()) }
            viewModel {(bundle:Bundle)-> ProductActivityViewModel(bundle) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModule)
        }
    }

}
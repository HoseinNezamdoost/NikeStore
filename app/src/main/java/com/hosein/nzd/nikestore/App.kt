package com.hosein.nzd.nikestore

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import com.facebook.drawee.backends.pipeline.Fresco
import com.hosein.nzd.nikestore.common.BadgeViewModel
import com.hosein.nzd.nikestore.data.repository.*
import com.hosein.nzd.nikestore.data.repository.source.*
import com.hosein.nzd.nikestore.feature.auth.AuthViewModel
import com.hosein.nzd.nikestore.feature.cart.CartFragmentViewModel
import com.hosein.nzd.nikestore.feature.cart.shipping.ShippingViewModel
import com.hosein.nzd.nikestore.feature.main.MainProductAdapter
import com.hosein.nzd.nikestore.feature.main.MainProductAdapterPopular
import com.hosein.nzd.nikestore.feature.main.MainViewModel
import com.hosein.nzd.nikestore.feature.main.productActivity.ProductDetailActivityViewModel
import com.hosein.nzd.nikestore.feature.main.productActivity.comment.ProductCommentViewModel
import com.hosein.nzd.nikestore.feature.main.productList.ProductListViewModel
import com.hosein.nzd.nikestore.services.http.createApiServiceInstance
import com.hosein.nzd.nikestore.services.loadImage.FrescoLoadImageService
import com.hosein.nzd.nikestore.services.loadImage.LoadImageService
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.lang.reflect.Array.get

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)

        val myModule = module {
            single <SharedPreferences>{ this@App.getSharedPreferences("app_setting" , MODE_PRIVATE) }
            single { createApiServiceInstance() }
            single <LoadImageService>{ FrescoLoadImageService() }
            single { UserLocalDataSource(get()) }
            factory <SubmitOrderRepository>{ SubmitOrderRepositoryImpl(SubmitRemoteDataSource(get())) }
            factory <ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()) , ProductLocalDataSource()) }
            factory <BannerRepository>{ BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory <CommentRepository>{ CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory <CartRepository>{ CartRepositoryImpl(CartRemoteDataSource(get())) }
            factory { (viewType:Int)->MainProductAdapter(viewType,get()) }
            factory { MainProductAdapterPopular(get()) }
            factory <UserRepository>{ UserRepositoryImpl(UserRemoteDataSource(get()) , UserLocalDataSource(get())) }
            viewModel { MainViewModel(get() , get()) }
            viewModel {(bundle:Bundle)-> ProductDetailActivityViewModel(bundle , get() , get()) }
            viewModel { (sort:Int) -> ProductCommentViewModel(sort , get()) }
            viewModel { (sort:Int) -> ProductListViewModel(sort , get())}
            viewModel { AuthViewModel(get()) }
            viewModel { CartFragmentViewModel(get()) }
            viewModel { BadgeViewModel(get()) }
            viewModel { ShippingViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModule)
        }

        val userRepository : UserRepository = get()
        userRepository.loadToken()

    }

}
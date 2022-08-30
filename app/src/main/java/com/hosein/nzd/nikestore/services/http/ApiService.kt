package com.hosein.nzd.nikestore.services.http

import com.google.gson.JsonObject
import com.hosein.nzd.nikestore.data.*
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @GET("product/list/")
    fun getProducts(@Query("sort") sort: String): Single<List<Product>>

    @GET("banner/slider")
    fun getBanners(): Single<List<Banner>>

    @GET("comment/list/")
    fun getComment(@Query("product_id") id: Int): Single<List<Comment>>

    @POST("cart/add")
    fun addToCart(@Body jsonObject: JsonObject): Single<AddToCartResponse>

    @POST("auth/token")
    fun login(@Body jsonObject: JsonObject): Single<TokenResponse>

    @POST("user/register")
    fun register(@Body jsonObject: JsonObject): Single<MessageResponse>

    @POST("auth/token")
    fun refreshToken(@Body jsonObject: JsonObject): Call<TokenResponse>
}

fun createApiServiceInstance(): ApiService {

    val http = OkHttpClient.Builder().addInterceptor {
        val oldRequest = it.request()
        val newRequest = oldRequest.newBuilder()
        if (TokenContainer.accessToken != null) {
            newRequest.addHeader("Authorization", "Bearer ${TokenContainer.accessToken}")
            newRequest.method(oldRequest.method() , oldRequest.body())
        }
        return@addInterceptor it.proceed(newRequest.build())
    }.build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(http)
        .build()

    return retrofit.create(ApiService::class.java)
}
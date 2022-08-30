package com.hosein.nzd.nikestore.data.repository.source

import com.google.gson.JsonObject
import com.hosein.nzd.nikestore.data.MessageResponse
import com.hosein.nzd.nikestore.data.TokenResponse
import com.hosein.nzd.nikestore.services.http.ApiService
import io.reactivex.rxjava3.core.Single

const val client_id = 2
const val client_secret = "kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC"

class UserRemoteDataSource(private val apiService: ApiService) : UserDataSource {

    override fun login(email: String, password: String): Single<TokenResponse> {
        return apiService.login(JsonObject().apply {
            addProperty("grant_type" , "password")
            addProperty("client_secret" , client_secret)
            addProperty("client_id" , client_id)
            addProperty("username" , email)
            addProperty("password" , password)
        })
    }

    override fun register(email: String, password: String): Single<MessageResponse> {
        return apiService.register(JsonObject().apply {
            addProperty("email" , email)
            addProperty("password" , password)
        })
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun saveToken(accessToken: String, refreshToken: String) {
        TODO("Not yet implemented")
    }
}
package com.hosein.nzd.nikestore.data.repository.source

import com.hosein.nzd.nikestore.data.MessageResponse
import com.hosein.nzd.nikestore.data.TokenResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UserDataSource {

    fun login(email: String, password: String): Single<TokenResponse>
    fun register(email: String, password: String): Single<MessageResponse>
    fun loadToken()
    fun saveToken(accessToken: String, refreshToken: String)
    fun saveUsername(username: String)
    fun getUsername(): String
    fun signOut()
}
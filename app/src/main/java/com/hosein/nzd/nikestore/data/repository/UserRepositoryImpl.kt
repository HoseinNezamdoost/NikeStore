package com.hosein.nzd.nikestore.data.repository

import com.hosein.nzd.nikestore.data.TokenContainer
import com.hosein.nzd.nikestore.data.TokenResponse
import com.hosein.nzd.nikestore.data.repository.source.UserLocalDataSource
import com.hosein.nzd.nikestore.data.repository.source.UserRemoteDataSource
import io.reactivex.rxjava3.core.Completable

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {


    override fun login(email: String, password: String): Completable {
        return userRemoteDataSource.login(email , password).doOnSuccess{
            onDoSuccess(it)
        }.ignoreElement()
    }

    override fun register(email: String, password: String): Completable {
        return userRemoteDataSource.register(email, password).flatMap {
            userRemoteDataSource.login(email , password)
        }.doOnSuccess{
            onDoSuccess(it)
        }.ignoreElement()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    private fun onDoSuccess(tokenResponse: TokenResponse){
        TokenContainer.update(tokenResponse.access_token , tokenResponse.refresh_token)
        userLocalDataSource.saveToken(tokenResponse.access_token , tokenResponse.refresh_token)
    }

}
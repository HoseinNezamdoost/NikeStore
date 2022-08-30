package com.hosein.nzd.nikestore.data.repository.source

import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import com.hosein.nzd.nikestore.data.MessageResponse
import com.hosein.nzd.nikestore.data.TokenContainer
import com.hosein.nzd.nikestore.data.TokenResponse
import io.reactivex.rxjava3.core.Single

const val access_token = "access_token"
const val refresh_token = "refresh_token"

class UserLocalDataSource(private val sharedPreferences: SharedPreferences) : UserDataSource {
    override fun login(email: String, password: String): Single<TokenResponse> {
        TODO("Not yet implemented")
    }

    override fun register(email: String, password: String): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(sharedPreferences.getString(access_token , null) , sharedPreferences.getString(
            refresh_token , null))
    }

    override fun saveToken(accessToken: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString(access_token , accessToken)
            putString(refresh_token , refreshToken)
        }.apply()
    }
}
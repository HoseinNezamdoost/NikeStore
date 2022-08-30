package com.hosein.nzd.nikestore.services.http

import com.google.gson.JsonObject
import com.hosein.nzd.nikestore.data.TokenContainer
import com.hosein.nzd.nikestore.data.TokenResponse
import com.hosein.nzd.nikestore.data.repository.source.UserDataSource
import com.hosein.nzd.nikestore.data.repository.source.client_id
import com.hosein.nzd.nikestore.data.repository.source.client_secret
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NikeAuthenticator : Authenticator, KoinComponent {

    val apiService: ApiService by inject()
    val userLocalDataSource: UserDataSource by inject()

    override fun authenticate(route: Route?, response: Response): Request? {

        try {
            if (TokenContainer.accessToken != null && TokenContainer.refreshToken != null && response.request()
                    .url().pathSegments().last().equals("auth", false)){

                val token = refreshToken()
                if (token.isNotEmpty())
                    return null

                return response.request().newBuilder().header("Authorization" , "Bearer $token").build()
            }
        } catch (e: Exception) {

        }
        return null
    }

    fun refreshToken(): String {
        val token: retrofit2.Response<TokenResponse> = apiService.refreshToken(JsonObject().apply {
            addProperty("grant_type", "refresh_token")
            addProperty("client_id", client_id)
            addProperty("client_secret", client_secret)
            addProperty("refresh_token", "Bearer ${TokenContainer.refreshToken}")
        }).execute()

        token.body()?.let {
            TokenContainer.update(it.access_token, it.refresh_token)
            userLocalDataSource.saveToken(it.access_token, it.refresh_token)
            return it.access_token
        }
        return ""
    }
}
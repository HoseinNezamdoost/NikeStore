package com.hosein.nzd.nikestore.data

import android.util.Log

object TokenContainer {
    var accessToken:String? = null
    private set
    var refreshToken:String? = null
    private set

    fun update(accessToken:String? , refreshToken:String?){
        Log.i("Token", "update: ${accessToken?.substring(0 , 10)} <-> ${refreshToken?.substring(0 , 10)}")
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }
}
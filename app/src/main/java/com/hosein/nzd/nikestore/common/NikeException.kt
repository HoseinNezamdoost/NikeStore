package com.hosein.nzd.nikestore.common

import androidx.annotation.StringRes
import retrofit2.HttpException

class NikeException(val type: Type, @StringRes val clientMessage: Int = 0, val serverMessage: String? = null) :
    Throwable() {

    enum class Type {
        SIMPLE, DIALOG, AUTH
    }
}
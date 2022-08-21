package com.hosein.nzd.nikestore.common

import android.util.Log
import com.hosein.nzd.nikestore.R
import org.json.JSONObject
import retrofit2.HttpException

class NikeExceptionMapper {

    companion object {
        fun map(throwable: Throwable): NikeException {
            if (throwable is HttpException) {
                try {
                    val errorResponse = JSONObject(throwable.response()?.errorBody()!!.string())
                    val errorMessage = errorResponse.getString("message")
                    return NikeException(if (throwable.code() == 401) NikeException.Type.AUTH else NikeException.Type.SIMPLE,
                        serverMessage = errorMessage)
                } catch (e: Exception) {
                    Log.e("E", "map: $e")
                }
            }
            return NikeException(NikeException.Type.SIMPLE , R.string.unKnown_error)
        }
    }

}
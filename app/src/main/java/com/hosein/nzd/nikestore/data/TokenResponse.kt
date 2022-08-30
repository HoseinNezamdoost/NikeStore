package com.hosein.nzd.nikestore.data

data class TokenResponse(
    var access_token: String,
    var expires_in: Int,
    var refresh_token: String,
    var token_type: String
)
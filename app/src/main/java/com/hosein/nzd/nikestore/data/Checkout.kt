package com.hosein.nzd.nikestore.data

data class Checkout(
    var payable_price: Int,
    var payment_status: String,
    var purchase_success: Boolean
)
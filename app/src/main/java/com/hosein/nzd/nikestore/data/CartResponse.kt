package com.hosein.nzd.nikestore.data

import com.hosein.nzd.nikestore.data.CartItem

data class CartResponse(
    val cart_items: List<CartItem>,
    val payable_price: Int,
    val shipping_cost: Int,
    val total_price: Int
)
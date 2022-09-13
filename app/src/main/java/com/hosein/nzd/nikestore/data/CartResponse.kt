package com.hosein.nzd.nikestore.data

import android.os.Parcelable
import com.hosein.nzd.nikestore.data.CartItem
import kotlinx.android.parcel.Parcelize

data class CartResponse(
    val cart_items: List<CartItem>,
    val payable_price: Int,
    val shipping_cost: Int,
    val total_price: Int
)
@Parcelize
data class PerchesDetail(var payable_price: Int, val shipping_cost: Int, var total_price: Int):Parcelable
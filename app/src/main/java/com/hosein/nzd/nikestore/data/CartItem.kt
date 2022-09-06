package com.hosein.nzd.nikestore.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartItem(
    val cart_item_id: Int,
    var count: Int,
    val product: Product,
    var showProgressChangeItemCount : Boolean
):Parcelable
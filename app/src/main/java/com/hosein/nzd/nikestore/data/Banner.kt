package com.hosein.nzd.nikestore.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Banner(
    var id: Int,
    var image: String,
    var link_type: Int,
    var link_value: String
):Parcelable
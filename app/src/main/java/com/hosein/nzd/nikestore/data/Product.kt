package com.hosein.nzd.nikestore.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favoriteProduct")
@Parcelize
data class Product(
    var discount: Int,
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var image: String,
    var previous_price: Int,
    var price: Int,
    var status: Int,
    var title: String
):Parcelable

const val SORT_LAST = 0
const val SORT_POPULAR = 1
const val SORT_DESC = 2
const val SORT_ASC = 3
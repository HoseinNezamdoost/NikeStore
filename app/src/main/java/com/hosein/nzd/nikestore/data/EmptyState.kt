package com.hosein.nzd.nikestore.data

import androidx.annotation.StringRes

data class EmptyState(
    val mustShow:Boolean,
    @StringRes val textEmptyState:Int = 0,
    val mustShowCallToAction: Boolean = false
)

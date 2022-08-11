package com.hosein.nzd.nikestore.feature.productActivity

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.hosein.nzd.nikestore.common.EXTRA_KEY_ID
import com.hosein.nzd.nikestore.common.NikeViewModel
import com.hosein.nzd.nikestore.data.Product

class ProductActivityViewModel(bundle: Bundle) : NikeViewModel() {
    val productLiveData = MutableLiveData<Product>()

    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_ID)
    }

}
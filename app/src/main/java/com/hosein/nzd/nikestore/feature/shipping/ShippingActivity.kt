package com.hosein.nzd.nikestore.feature.shipping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.EXTRA_KEY_ID
import com.hosein.nzd.nikestore.data.PerchesDetail
import com.hosein.nzd.nikestore.feature.cart.CartFragmentAdapter
import kotlinx.android.synthetic.main.activity_shipping.*
import kotlinx.android.synthetic.main.item_purchase_details.view.*

class ShippingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)

        val perchesDetail = intent.getParcelableExtra<PerchesDetail>(EXTRA_KEY_ID)?:IllegalStateException("PerchesDetail is Empty")

        CartFragmentAdapter.CartPerchesViewHolder(purchaseDetailView).bindPerchesItem(perchesDetail as PerchesDetail)
        toolbar_shipping_activity.onBackButtonToolbarNk = View.OnClickListener {
            finish()
        }
    }
}
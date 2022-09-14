package com.hosein.nzd.nikestore.feature.cart.shipping.checkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hosein.nzd.nikestore.R
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        toolbarCheckout.onBackButtonToolbarNk = View.OnClickListener {
            finish()
        }
    }
}
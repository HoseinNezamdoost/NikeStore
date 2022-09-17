package com.hosein.nzd.nikestore.feature.cart.shipping.checkout

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.EXTRA_KEY_ID
import com.hosein.nzd.nikestore.common.NikeActivity
import com.hosein.nzd.nikestore.common.formatPrice
import kotlinx.android.synthetic.main.activity_checkout.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckoutActivity : NikeActivity() {

    val viewModel : CheckoutViewModel by viewModel {
        val uri : Uri? = intent.data
        if (uri!=null)
            parametersOf(uri.getQueryParameter("order_id")?.toInt())
        else
            parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        toolbarCheckout.onBackButtonToolbarNk = View.OnClickListener {
            finish()
        }

        viewModel.checkoutLiveData.observe(this){
            purchaseStatusTv.text = if (it.purchase_success) "خرید با موفقیت انجام شد" else "خرید نا موفق"
            orderStatusTv.text = it.payment_status
            orderPriceTv.text = formatPrice(it.payable_price)
        }
        returnHomeBtn.setOnClickListener {
            finish()
        }
        orderHistoryBtn.setOnClickListener {
            Toast.makeText(this, "این بخش در حال توسعه دادن است", Toast.LENGTH_SHORT).show()
        }
    }
}
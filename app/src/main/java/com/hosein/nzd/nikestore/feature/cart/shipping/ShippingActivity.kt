package com.hosein.nzd.nikestore.feature.cart.shipping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.EXTRA_KEY_ID
import com.hosein.nzd.nikestore.common.NikeSingleObservable
import com.hosein.nzd.nikestore.common.openUrlInCustomTab
import com.hosein.nzd.nikestore.data.PerchesDetail
import com.hosein.nzd.nikestore.data.SubmitOrderResult
import com.hosein.nzd.nikestore.feature.cart.CartFragmentAdapter
import com.hosein.nzd.nikestore.feature.cart.shipping.checkout.CheckoutActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.activity_shipping.*
import org.koin.androidx.viewmodel.ext.android.viewModel

const val ORDER_SUBMIT_ONLINE = "online"
const val ORDER_SUBMIT_COD = "cash_on_delivery"

class ShippingActivity : AppCompatActivity() {

    val viewModel: ShippingViewModel by viewModel()
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)

        val perchesDetail = intent.getParcelableExtra<PerchesDetail>(EXTRA_KEY_ID)
            ?: IllegalStateException("PerchesDetail is Empty")

        CartFragmentAdapter.CartPerchesViewHolder(purchaseDetailView)
            .bindPerchesItem(perchesDetail as PerchesDetail)
        toolbar_shipping_activity.onBackButtonToolbarNk = View.OnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun onClickSubmit(view: View) {

        if (phoneNumberEt.length() < 11)
            Toast.makeText(this, "شماره تلفن باید یازده رقم باشد", Toast.LENGTH_SHORT).show()
        else if (addressEt.length() < 20)
            Toast.makeText(this, "ادرس باید بیش از بیست کاراکتر باشد", Toast.LENGTH_SHORT).show()
        else if (postalCodeEt.length () < 10)
            Toast.makeText(this, "کدپستی باید ده رقم باشد", Toast.LENGTH_SHORT).show()
        else {
            viewModel.submit(
                firstNameEt.text.toString(),
                lastNameEt.text.toString(),
                postalCodeEt.text.toString(),
                phoneNumberEt.text.toString(),
                addressEt.text.toString(),
                if (view.id == R.id.onlinePaymentBtn) ORDER_SUBMIT_ONLINE else ORDER_SUBMIT_COD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NikeSingleObservable<SubmitOrderResult>(compositeDisposable) {
                    override fun onSuccess(t: SubmitOrderResult) {
                        if (t.bank_gateway_url.isNotEmpty()) {
                            openUrlInCustomTab(this@ShippingActivity, t.bank_gateway_url)
                        } else {
                            startActivity(Intent(this@ShippingActivity,
                                CheckoutActivity::class.java).apply {
                                putExtra(EXTRA_KEY_ID, t.order_id)
                            })
                        }
                        finish()
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        Toast.makeText(this@ShippingActivity,
                            "لطفا فیلد هارا به درستی وارد کنید",
                            Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }
}
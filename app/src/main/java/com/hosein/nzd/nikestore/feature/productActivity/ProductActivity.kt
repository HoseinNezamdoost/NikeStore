package com.hosein.nzd.nikestore.feature.productActivity

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.formatPrice
import com.hosein.nzd.nikestore.services.loadImage.LoadImageService
import com.hosein.nzd.nikestore.view.scroll.ObservableScrollViewCallbacks
import com.hosein.nzd.nikestore.view.scroll.ScrollState
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.activity_product.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductActivity : AppCompatActivity() {

    val productActivityViewModel : ProductActivityViewModel by viewModel{parametersOf(intent.extras)}
    val loadImageService : LoadImageService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        productActivityViewModel.productLiveData.observe(this){
            loadImageService.load(productIv , it.image)
            titleTv.text = it.title
            previousPriceTv.text = formatPrice(it.previous_price)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            currentPriceTv.text = formatPrice(it.price)
            toolbarTitleTv.text = it.title
        }

        productIv.post {
            val imageView = productIv
            val imageViewHeight = imageView.height

            observableScrollView.addScrollViewCallbacks(object : ObservableScrollViewCallbacks{
                override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
                    imageView.translationY = scrollY.toFloat() / 2
                    toolbarView.alpha = scrollY.toFloat() / imageViewHeight.toFloat()
                }

                override fun onDownMotionEvent() {
                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
                }

            })
        }
    }
}
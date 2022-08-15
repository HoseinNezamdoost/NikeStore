package com.hosein.nzd.nikestore.feature.main.productActivity

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.EXTRA_PASS
import com.hosein.nzd.nikestore.common.NikeActivity
import com.hosein.nzd.nikestore.common.formatPrice
import com.hosein.nzd.nikestore.data.Comment
import com.hosein.nzd.nikestore.feature.main.productActivity.comment.ProductActivityComment
import com.hosein.nzd.nikestore.services.loadImage.LoadImageService
import com.hosein.nzd.nikestore.view.scroll.ObservableScrollViewCallbacks
import com.hosein.nzd.nikestore.view.scroll.ScrollState
import kotlinx.android.synthetic.main.activity_product.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductActivity : NikeActivity() {

    val productActivityViewModel : ProductActivityViewModel by viewModel{parametersOf(intent.extras)}
    val loadImageService : LoadImageService by inject()
    val adapterComment = ProductAdapterComment()

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

        productActivityViewModel.commentLiveData.observe(this){
            adapterComment.comments = it as ArrayList<Comment>
            if(it.size > 5) viewAllCommentsBtn.visibility= View.VISIBLE
            viewAllCommentsBtn.setOnClickListener {
                startActivity(Intent(this , ProductActivityComment::class.java).apply {
                    putExtra(EXTRA_PASS , productActivityViewModel.productLiveData.value!!.id)
                })
            }
        }

        productActivityViewModel.progressBraLiveData.observe(this){
            setProgressIndicator(it)
        }

        commentsRv.layoutManager = LinearLayoutManager(this , RecyclerView.VERTICAL , false)
        commentsRv.adapter = adapterComment

        toolbarTitleNkProduct.onBackButtonToolbarNk = View.OnClickListener {
            finish()
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
package com.hosein.nzd.nikestore.feature.main.productActivity.comment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.EXTRA_PASS
import com.hosein.nzd.nikestore.common.NikeActivity
import com.hosein.nzd.nikestore.data.Comment
import com.hosein.nzd.nikestore.feature.main.productActivity.ProductAdapterComment
import kotlinx.android.synthetic.main.activity_product_comment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductActivityComment : NikeActivity() {

    val viewModel : ProductCommentViewModel by viewModel{parametersOf(intent.extras!!.getInt(EXTRA_PASS))}
    val adapter = ProductAdapterComment(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_comment)

        toolbarTitleNk.onBackButtonToolbarNk = View.OnClickListener {
            finish()
        }

        viewModel.progressBraLiveData.observe(this){
            setProgressIndicator(it)
        }

        viewModel.liveData.observe(this){
            commentsRv.layoutManager = LinearLayoutManager(this , RecyclerView.VERTICAL , false)
            adapter.comments = it as ArrayList<Comment>
            commentsRv.adapter = adapter
        }

    }
}
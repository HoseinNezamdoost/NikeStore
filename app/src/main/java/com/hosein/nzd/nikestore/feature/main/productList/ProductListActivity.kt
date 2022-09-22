package com.hosein.nzd.nikestore.feature.main.productList

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.EXTRA_KEY_ID
import com.hosein.nzd.nikestore.common.EXTRA_PASS
import com.hosein.nzd.nikestore.common.NikeActivity
import com.hosein.nzd.nikestore.data.Product
import com.hosein.nzd.nikestore.feature.main.MainProductAdapter
import com.hosein.nzd.nikestore.feature.main.VIEW_TYPE_LARGE
import com.hosein.nzd.nikestore.feature.main.VIEW_TYPE_SMALL
import com.hosein.nzd.nikestore.feature.main.productActivity.ProductDetailActivity
import kotlinx.android.synthetic.main.activity_product_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductListActivity : NikeActivity() , MainProductAdapter.OnProductListClickListener{

    private val viewModel : ProductListViewModel by viewModel{ parametersOf(intent.extras!!.getInt(
        EXTRA_PASS))}
    private val adapter : MainProductAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        var gridLayout =  GridLayoutManager(this , 2)
        productsRv.layoutManager = gridLayout
        productsRv.adapter = adapter

        viewModel.productListLiveData.observe(this){
            adapter.productsLast = it as ArrayList<Product>
        }

        viewModel.progressBraLiveData.observe(this){
            setProgressIndicator(it)
        }

        toolbarView.onBackButtonToolbarNk = View.OnClickListener {
            finish()
        }

        //onClick for change show item products
        viewTypeChangerBtn.setOnClickListener {
            when(adapter.viewType){
                VIEW_TYPE_LARGE -> {
                    viewTypeChangerBtn.setImageResource(R.drawable.ic_grid)
                    adapter.viewType = VIEW_TYPE_SMALL
                    gridLayout.spanCount = 2
                    adapter.notifyDataSetChanged()
                }
                VIEW_TYPE_SMALL -> {
                    viewTypeChangerBtn.setImageResource(R.drawable.ic_view_type_large)
                    gridLayout.spanCount = 1
                    adapter.viewType = VIEW_TYPE_LARGE
                    adapter.notifyDataSetChanged()
                }
            }
        }

        //dialog for change sort products
        sortBtn.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems(R.array.sortArray , viewModel.sort) {
                        dialogInterface, indexItemSelected ->
                        dialogInterface.dismiss()
                    viewModel.selectedItemByUser(indexItemSelected)
                    }
                .setTitle(R.string.sort)
                .show()
        }

        viewModel.selectItemSortTitle.observe(this){
            selectedSortTitleTv.text = getString(it)
        }

        adapter.onProductListClickListener = this

    }

    override fun onClick(product: Product) {
        startActivity(Intent(this , ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_ID , product)
        })
    }

    override fun onFavoriteClick(product: Product) {
        TODO("Not yet implemented")
    }
}
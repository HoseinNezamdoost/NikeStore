package com.hosein.nzd.nikestore.feature.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.EXTRA_KEY_ID
import com.hosein.nzd.nikestore.common.EXTRA_PASS
import com.hosein.nzd.nikestore.common.NikeFragment
import com.hosein.nzd.nikestore.common.convertDpToPixel
import com.hosein.nzd.nikestore.data.Product
import com.hosein.nzd.nikestore.data.SORT_LAST
import com.hosein.nzd.nikestore.data.SORT_POPULAR
import com.hosein.nzd.nikestore.feature.main.productActivity.ProductDetailActivity
import com.hosein.nzd.nikestore.feature.main.productList.ProductListActivity
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Runnable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*
import kotlin.collections.ArrayList

class MainFragment : NikeFragment() , MainProductAdapter.OnProductListClickListener , MainProductAdapterPopular.OnClickProductPopular {

    private val viewModle: MainViewModel by viewModel()
    val mainProductAdapter :MainProductAdapter by inject{ parametersOf(VIEW_TYPE_ROUND)}
    val mainProductAdapterPopular :MainProductAdapterPopular by inject()
    val handler = Handler()
    lateinit var localVariableRunnable : Runnable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initialize interface OnProductListClickListener in adapter
        mainProductAdapter.onProductListClickListener = this
        mainProductAdapterPopular.onClickProductPopular = this

        popular_rc.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
        popular_rc.adapter = mainProductAdapterPopular

        lastProduct_rc.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
        lastProduct_rc.adapter = mainProductAdapter

        //for observe popular product
        viewModle.productLiveDataPopular.observe(viewLifecycleOwner){
            mainProductAdapterPopular.products = it as ArrayList<Product>
        }

        //for observe last product
        viewModle.productLiveDataLast.observe(viewLifecycleOwner) {
            mainProductAdapter.productsLast = it as ArrayList<Product>
        }

        //for observe banner data
        viewModle.bannerLiveData.observe(viewLifecycleOwner) {

            val mainBannerAdapter = MainBannerAdapter(this, it)
            sliderViewPager.adapter = mainBannerAdapter

            //(viewPagerWith * heightImage) / widthImage
            sliderViewPager.post {
                val viewPagerHeight = ((sliderViewPager.width - convertDpToPixel(32f , requireContext())).toInt() * 173) / 328
                sliderViewPager.layoutParams.height = viewPagerHeight
            }

            //indicator for slider
            indicatorSlider.setViewPager2(sliderViewPager)

            //for swipe auto on slider
            val runnable = Runnable {
                if (sliderViewPager.currentItem == it.size-1)
                    sliderViewPager.currentItem = 0
                else
                    sliderViewPager.currentItem = sliderViewPager.currentItem + 1
            }
            localVariableRunnable = runnable

            sliderViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    handler.removeCallbacks(runnable)
                    handler.postDelayed(runnable , 2000 )
                }
            })
        }

        //for observe on progressBar witch loading or no loading
        viewModle.progressBraLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        //intent to ProductListActivity
        button_viewAll_last.setOnClickListener {
            startActivity(Intent(requireContext() , ProductListActivity::class.java).apply {
                putExtra(EXTRA_PASS , SORT_LAST)
            })
        }

        button_viewAll_popular.setOnClickListener {
            startActivity(Intent(requireContext() , ProductListActivity::class.java).apply {
                putExtra(EXTRA_PASS , SORT_POPULAR)
            })
        }

        filterRecyclerView()

    }

    fun filterRecyclerView(){
        edt_search_main.doOnTextChanged { text, _, _, _ ->
            val query = text.toString().toLowerCase(Locale.getDefault())
            filterQuery(query)
        }
    }

    fun filterQuery(query:String){
        if (query.isNotEmpty()){
            val filteredListLast = onListChangeLast(query)
            val filteredListPopular = onListChangePopular(query)
            mainProductAdapter.productsLast = filteredListLast
            mainProductAdapterPopular.products = filteredListPopular
        }else{
            mainProductAdapter.productsLast = viewModle.productLiveDataLast.value as ArrayList<Product>
            mainProductAdapterPopular.products = viewModle.productLiveDataPopular.value as ArrayList<Product>
        }

    }

    fun onListChangeLast(filterQuery:String):ArrayList<Product>{
        val filterList = ArrayList<Product>()
        for (currentItem in mainProductAdapter.productsLast){
            if (currentItem.title.toLowerCase(Locale.getDefault()).contains(filterQuery)){
                filterList.add(currentItem)
            }
        }

        return filterList
    }

    fun onListChangePopular(filterQuery:String):ArrayList<Product>{
        val filterList = ArrayList<Product>()
        for (currentItem in mainProductAdapterPopular.products){
            if (currentItem.title.toLowerCase(Locale.getDefault()).contains(filterQuery)){
                filterList.add(currentItem)
            }
        }

        return filterList
    }

    //for remove (shutdown) slider
    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(localVariableRunnable)
    }

    override fun onPause() {
        super.onPause()
        handler.postDelayed(localVariableRunnable , 2000 )
    }

    //onClick for intent to product activity

    override fun onClick(product: Product) {
        startActivity(Intent(requireContext() , ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_ID , product)
        })
    }

    override fun onFavoriteClick(product: Product) {
        viewModle.insert(product)
    }

    override fun onClickProductPopular(product: Product) {
        startActivity(Intent(requireContext() , ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_ID , product)
        })
    }

}
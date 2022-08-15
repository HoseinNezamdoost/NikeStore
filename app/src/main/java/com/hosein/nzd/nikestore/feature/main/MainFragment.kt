package com.hosein.nzd.nikestore.feature.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.EXTRA_KEY_ID
import com.hosein.nzd.nikestore.common.NikeFragment
import com.hosein.nzd.nikestore.common.convertDpToPixel
import com.hosein.nzd.nikestore.data.Product
import com.hosein.nzd.nikestore.feature.main.productActivity.ProductActivity
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Runnable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.collections.ArrayList

class MainFragment : NikeFragment() , MainProductAdapter.OnProductListClickListener , MainProductAdapterPopular.OnClickProductPopular {

    private val mainViewModel: MainViewModel by viewModel()
    val mainProductAdapter :MainProductAdapter by inject()
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
        mainViewModel.productLiveDataPopular.observe(viewLifecycleOwner){
            mainProductAdapterPopular.products = it as ArrayList<Product>
        }

        //for observe last product
        mainViewModel.productLiveDataLast.observe(viewLifecycleOwner) {
            mainProductAdapter.productsLast = it as ArrayList<Product>
        }

        //for observe banner data
        mainViewModel.bannerLiveData.observe(viewLifecycleOwner) {

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
        mainViewModel.progressBraLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

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
        startActivity(Intent(requireContext() , ProductActivity::class.java).apply {
            putExtra(EXTRA_KEY_ID , product)
        })
    }

    override fun onClickProductPopular(product: Product) {
        startActivity(Intent(requireContext() , ProductActivity::class.java).apply {
            putExtra(EXTRA_KEY_ID , product)
        })
    }

}
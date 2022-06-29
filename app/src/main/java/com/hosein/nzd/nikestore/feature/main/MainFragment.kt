package com.hosein.nzd.nikestore.feature.main

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.NikeFragment
import com.hosein.nzd.nikestore.common.convertDpToPixel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Runnable
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.sql.Time
import java.util.*
import kotlin.concurrent.thread

class MainFragment : NikeFragment() {

    private val mainViewModel: MainViewModel by viewModel()
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

        mainViewModel.productLiveData.observe(viewLifecycleOwner) {
            Log.i("MainFragments", "product: $it")
        }

        //for observe banner data
        mainViewModel.bannerLiveData.observe(viewLifecycleOwner) {

            val mainBannerAdapter = MainBannerAdapter(this, it)
            sliderViewPager.adapter = mainBannerAdapter

            //indicator for slider
            indicatorSlider.setViewPager2(sliderViewPager)

            //(viewPagerWith * heightImage) / widthImage
            val viewPagerHeight = ((sliderViewPager.width - convertDpToPixel(32f , requireContext())).toInt() * 173) / 328
            sliderViewPager.layoutParams.height = viewPagerHeight

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
        //handler.removeCallbacks(localVariableRunnable)
    }

    override fun onPause() {
        super.onPause()
        //handler.postDelayed(localVariableRunnable , 2000 )
    }

}
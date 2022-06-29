package com.hosein.nzd.nikestore.feature.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hosein.nzd.nikestore.data.Banner

class MainBannerAdapter(fragment: Fragment, val banners: List<Banner>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = banners.size

    override fun createFragment(position: Int): Fragment = MainBannerSliderFragment.newInstance(banners[position])

}
package com.hosein.nzd.nikestore.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.EXTRA_KEY_ID
import com.hosein.nzd.nikestore.data.Banner
import com.hosein.nzd.nikestore.services.loadImage.LoadImageService
import com.hosein.nzd.nikestore.view.NikeImageView
import org.koin.android.ext.android.inject

class MainBannerSliderFragment : Fragment() {

    val loadImageService: LoadImageService by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val imageView = inflater.inflate(R.layout.fragment_banner_slider, container, false) as NikeImageView

        val banner = requireArguments().getParcelable<Banner>(EXTRA_KEY_ID)
            ?: throw IllegalAccessException("argument banner cannot empty")

        loadImageService.load(imageView, banner.image)

        return imageView
    }

    companion object {
        fun newInstance(banner: Banner): MainBannerSliderFragment {

            //top code like belo code

            /*val mainBannerSliderFragment = MainBannerSliderFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_KEY_ID, banner)
            mainBannerSliderFragment.arguments = bundle*/

            return MainBannerSliderFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_KEY_ID , banner)
                }
            }

        }
    }

}
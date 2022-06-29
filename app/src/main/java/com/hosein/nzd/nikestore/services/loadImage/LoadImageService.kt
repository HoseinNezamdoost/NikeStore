package com.hosein.nzd.nikestore.services.loadImage

import android.widget.ImageView
import com.hosein.nzd.nikestore.view.NikeImageView

interface LoadImageService {
    fun load(imageView: ImageView, imageUrl: String)
}
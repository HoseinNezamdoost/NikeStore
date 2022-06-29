package com.hosein.nzd.nikestore.services.loadImage

import android.widget.ImageView
import com.facebook.drawee.backends.pipeline.Fresco
import com.hosein.nzd.nikestore.view.NikeImageView

class FrescoLoadImageService : LoadImageService {
    override fun load(imageView: ImageView, imageUrl: String) {
        if (imageView is NikeImageView)
            imageView.setImageURI(imageUrl)
        else
            throw IllegalAccessException("imageView is not cannot like NikeImageView")
    }
}
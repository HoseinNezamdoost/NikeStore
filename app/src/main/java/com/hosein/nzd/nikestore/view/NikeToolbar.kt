package com.hosein.nzd.nikestore.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.hosein.nzd.nikestore.R
import kotlinx.android.synthetic.main.view_toolbar.view.*

class NikeToolbar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    var onBackButtonToolbarNk : View.OnClickListener? = null
    set(value) {
        field = value
        backBtn.setOnClickListener(onBackButtonToolbarNk)
    }

    init {
        inflate(context , R.layout.view_toolbar , this)

        val a = context.obtainStyledAttributes(attrs , R.styleable.NikeToolbar)
        val nk_title = a.getString(R.styleable.NikeToolbar_nk_title)

        if(attrs != null && nk_title!!.isNotEmpty()){
            val titleToolbar = findViewById<TextView>(R.id.toolbarTitleTvNk)
            titleToolbar.text = nk_title
        }
        a.recycle()
    }

}
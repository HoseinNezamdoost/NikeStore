package com.hosein.nzd.nikestore

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class NikeActivity : AppCompatActivity(), NikeView {
    override fun progressIndicator(mostShow: Boolean) {
        TODO("Not yet implemented")
    }
}

abstract class NikeFragment : Fragment(), NikeView {
    override fun progressIndicator(mostShow: Boolean) {
        TODO("Not yet implemented")
    }
}

abstract class NikeViewModel : ViewModel() {

}

interface NikeView {
    fun progressIndicator(mostShow: Boolean)
}
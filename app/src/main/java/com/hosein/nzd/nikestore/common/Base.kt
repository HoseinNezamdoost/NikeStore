package com.hosein.nzd.nikestore.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

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

    val disposable = CompositeDisposable()

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}

interface NikeView {
    fun progressIndicator(mostShow: Boolean)
}
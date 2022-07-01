package com.hosein.nzd.nikestore.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hosein.nzd.nikestore.R
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class NikeActivity : AppCompatActivity(), NikeView {
    override val rootView: CoordinatorLayout?
        get() = window.decorView.rootView as CoordinatorLayout

    override val viewContext: Context?
        get() = this
}

abstract class NikeFragment : Fragment(), NikeView {
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout

    override val viewContext: Context?
        get() = context

}

abstract class NikeViewModel : ViewModel() {

    companion object{
        val disposable = CompositeDisposable()
        var progressBraLiveData = MutableLiveData<Boolean>()
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}

interface NikeView {
    val rootView: CoordinatorLayout?
    val viewContext: Context?

    fun setProgressIndicator(mostShow: Boolean) {
        //rootView != null
        rootView?.let {
            //viewContext != null
            viewContext?.let { viewContext ->
                var loadingView = it.findViewById<View>(R.id.loadingView)
                if (loadingView == null && mostShow) {
                    loadingView =
                        LayoutInflater.from(viewContext).inflate(R.layout.loading_view, it, false)
                    it.addView(loadingView)
                }
                loadingView?.visibility = if (mostShow) View.VISIBLE else View.GONE
            }
        }
    }


}
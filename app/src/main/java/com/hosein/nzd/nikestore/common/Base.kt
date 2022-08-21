package com.hosein.nzd.nikestore.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.feature.auth.AuthActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class NikeActivity : AppCompatActivity(), NikeView {
    override val rootView: CoordinatorLayout?
        get() {
            val viewGroup = window.decorView.findViewById(android.R.id.content) as ViewGroup
            if (viewGroup !is CoordinatorLayout) {
                viewGroup.children.forEach {
                    if (it is CoordinatorLayout) {
                        return it
                    }
                    throw IllegalAccessException("LayoutParent must be instance of CoordinatorLayout")
                }
            } else
                return viewGroup
            throw IllegalAccessException("LayoutParent must be instance of CoordinatorLayout")
        }

    override val viewContext: Context?
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

}

abstract class NikeFragment : Fragment(), NikeView {
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout

    override val viewContext: Context?
        get() = context

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}

abstract class NikeViewModel : ViewModel() {

    val disposable = CompositeDisposable()
    val progressBraLiveData = MutableLiveData<Boolean>()

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showErrors(nikeException: NikeException){
        viewContext?.let {
            when(nikeException.type){
                NikeException.Type.SIMPLE -> {
                    showSnackBar(nikeException.serverMessage ?: it.getString(nikeException.clientMessage) )
                }

                NikeException.Type.AUTH -> {
                    it.startActivity(Intent(it , AuthActivity::class.java))
                    Toast.makeText(it , nikeException.serverMessage , Toast.LENGTH_LONG).show()
                }
                else -> throw IllegalAccessException(it.getString(R.string.unKnown_error))
            }
        }
    }

    fun showSnackBar(message:String , duration: Int = Snackbar.LENGTH_LONG){
        rootView?.let {
            Snackbar.make(it, message , duration).show()
        }

    }

}
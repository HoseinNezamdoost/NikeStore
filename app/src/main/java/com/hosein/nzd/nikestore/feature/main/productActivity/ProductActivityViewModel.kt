package com.hosein.nzd.nikestore.feature.main.productActivity

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.hosein.nzd.nikestore.common.EXTRA_KEY_ID
import com.hosein.nzd.nikestore.common.NikeSingleObservable
import com.hosein.nzd.nikestore.common.NikeViewModel
import com.hosein.nzd.nikestore.data.Comment
import com.hosein.nzd.nikestore.data.Product
import com.hosein.nzd.nikestore.data.repository.CommentRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductActivityViewModel(bundle: Bundle , commentRepository: CommentRepository) : NikeViewModel() {
    val productLiveData = MutableLiveData<Product>()
    val commentLiveData = MutableLiveData<List<Comment>>()

    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_ID)
        progressBraLiveData.value = true
        commentRepository.getAll(productLiveData.value!!.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {progressBraLiveData.value = false }
            .subscribe(object : NikeSingleObservable<List<Comment>>(disposable){
                override fun onSuccess(t: List<Comment>) {
                    commentLiveData.value = t
                }
            })
    }

}
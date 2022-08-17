package com.hosein.nzd.nikestore.feature.main.productActivity.comment

import androidx.lifecycle.MutableLiveData
import com.hosein.nzd.nikestore.common.NikeSingleObservable
import com.hosein.nzd.nikestore.common.NikeViewModel
import com.hosein.nzd.nikestore.data.Comment
import com.hosein.nzd.nikestore.data.repository.CommentRepository
import com.hosein.nzd.nikestore.services.http.ApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductCommentViewModel(val sort:Int , val commentRepository: CommentRepository) : NikeViewModel() {

    val liveData = MutableLiveData<List<Comment>>()

    init {
        progressBraLiveData.value = true
        commentRepository.getAll(sort)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBraLiveData.value = false }
            .subscribe(object : NikeSingleObservable<List<Comment>>(disposable){
                override fun onSuccess(t: List<Comment>) {
                    liveData.value = t
                }
            })
    }

}
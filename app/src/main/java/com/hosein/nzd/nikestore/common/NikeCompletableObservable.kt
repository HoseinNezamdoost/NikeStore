package com.hosein.nzd.nikestore.common

import android.util.Log
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class NikeCompletableObservable(val compositeDisposable: CompositeDisposable) : CompletableObserver {
    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
        Log.e("NikeCompletableObservable", "onError: $e")
    }
}
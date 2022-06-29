package com.hosein.nzd.nikestore.common

import android.util.Log
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class NikeSingleObservable<T>(val disposable: CompositeDisposable):SingleObserver<T> {

    override fun onSubscribe(d: Disposable) {
        disposable.add(d)
    }

    override fun onError(e: Throwable) {
        Log.i("error", "onError: $e")
    }
}
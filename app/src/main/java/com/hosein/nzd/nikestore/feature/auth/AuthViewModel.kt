package com.hosein.nzd.nikestore.feature.auth

import com.hosein.nzd.nikestore.common.NikeViewModel
import com.hosein.nzd.nikestore.data.repository.UserRepository
import io.reactivex.rxjava3.core.Completable

class AuthViewModel(private val repository: UserRepository) : NikeViewModel() {

    fun login(email: String, password: String): Completable {
        progressBraLiveData.value = true
        return repository.login(email, password).doFinally {
            progressBraLiveData.postValue(false)
        }
    }

    fun register(email: String, password: String): Completable {
        progressBraLiveData.value = true
        return repository.register(email, password).doFinally {
            progressBraLiveData.postValue(false)
        }
    }

}
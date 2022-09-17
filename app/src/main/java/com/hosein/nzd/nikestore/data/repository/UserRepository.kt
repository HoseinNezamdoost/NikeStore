package com.hosein.nzd.nikestore.data.repository

import io.reactivex.rxjava3.core.Completable

interface UserRepository {

    fun login(email:String , password:String):Completable
    fun register(email:String , password:String):Completable
    fun loadToken()
    fun getUsername() : String
    fun saveUsername(username:String)
    fun signOut()
}
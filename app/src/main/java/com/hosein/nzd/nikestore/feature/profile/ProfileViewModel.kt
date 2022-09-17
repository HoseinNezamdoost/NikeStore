package com.hosein.nzd.nikestore.feature.profile

import com.hosein.nzd.nikestore.common.NikeViewModel
import com.hosein.nzd.nikestore.data.TokenContainer
import com.hosein.nzd.nikestore.data.repository.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) : NikeViewModel() {
    val username : String
    get() = userRepository.getUsername()

    val isCheckLogin : Boolean
        get() =  TokenContainer.accessToken != null

    fun signOut() = userRepository.signOut()
}
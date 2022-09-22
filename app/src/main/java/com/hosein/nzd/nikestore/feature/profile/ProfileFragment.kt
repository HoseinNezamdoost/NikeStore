package com.hosein.nzd.nikestore.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.NikeFragment
import com.hosein.nzd.nikestore.feature.auth.AuthActivity
import com.hosein.nzd.nikestore.feature.profile.favorite.FavoriteActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment: NikeFragment() {

    val viewModel : ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderRecorde.setOnClickListener {
            Toast.makeText(requireContext(), "این بخش درحال توسعه است", Toast.LENGTH_SHORT).show()
        }

        favoriteList.setOnClickListener {
            startActivity(Intent(requireContext() , FavoriteActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        checkLogin()
    }

    private fun checkLogin() {
        if (viewModel.isCheckLogin){
            authBtn.text = getString(R.string.signOut)
            usernameTv.text = viewModel.username
            authBtn.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sign_out , 0)
            authBtn.setOnClickListener {
                viewModel.signOut()
                checkLogin()
            }
        }else{
            authBtn.text = getString(R.string.signIn)
            usernameTv.text = getString(R.string.guest_user)
            authBtn.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sign_in , 0)
            authBtn.setOnClickListener {
                startActivity(Intent(requireContext() , AuthActivity::class.java))
            }
        }
    }

}
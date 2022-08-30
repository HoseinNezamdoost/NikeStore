package com.hosein.nzd.nikestore.feature.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.NikeCompletableObservable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern

class LoginFragment : Fragment() {

    val viewModel: AuthViewModel by viewModel()
    val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toRegisterPageBtn.setOnClickListener {
            (requireActivity() as AuthActivity).fragmentManager(RegisterFragment())
        }

        loginBtn.setOnClickListener {
            val email = emailEdt.text.toString()
            val password = passEdt.text.toString()
            if (email.length > 0 && password.length > 0) {
                if (isMatchEmail(email)){
                    viewModel.login(email, password)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : NikeCompletableObservable(compositeDisposable) {
                            override fun onComplete() {
                                (requireActivity() as AuthActivity).finish()
                            }
                        })
                }else{
                    emailEdt.setError(getString(R.string.no_match_email))
                }
            } else {
                emailEdt.setError(getString(R.string.error_email_edt))
                passEdt.setError(getString(R.string.error_pass_edt))
            }
        }

    }

    fun isMatchEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

}
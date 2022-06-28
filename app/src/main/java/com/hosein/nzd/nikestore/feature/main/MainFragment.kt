package com.hosein.nzd.nikestore.feature.main

import android.database.Observable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.NikeFragment
import com.hosein.nzd.nikestore.data.Product
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : NikeFragment() {

    private val mainViewModel : MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.productLiveData.observe(this){
            Log.i("MainFragments", "onViewCreated: $it")
        }
    }

}
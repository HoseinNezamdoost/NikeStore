package com.hosein.nzd.nikestore.feature.main

import android.os.Bundle
import android.util.Log
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.NikeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : NikeActivity() {
    private val mainViewModel : MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.productLiveData.observe(this){
            Log.i("MainFragments", "onViewCreated: $it")
        }
    }
}
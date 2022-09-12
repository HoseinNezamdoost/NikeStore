package com.hosein.nzd.nikestore.feature.main

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hosein.nzd.nikestore.R
import com.hosein.nzd.nikestore.common.NikeActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.color.MaterialColors
import com.hosein.nzd.nikestore.common.BadgeViewModel
import com.hosein.nzd.nikestore.common.convertDpToPixel
import com.hosein.nzd.nikestore.common.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : NikeActivity() {

    private var currentNavController: LiveData<NavController>? = null
    private val viewModel : BadgeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnBadgeCartItem()
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation_main)

        val navGraphIds = listOf(R.navigation.home, R.navigation.cart, R.navigation.profile)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    fun setOnBadgeCartItem(){
        viewModel.badgeCountLiveData.observe(this){
            val badge = bottomNavigation_main.getOrCreateBadge(R.id.cart)
            badge.badgeGravity = BadgeDrawable.BOTTOM_START
            badge.backgroundColor = getColor(R.color.blue)
            badge.number = it
            badge.verticalOffset= convertDpToPixel(10f,this).toInt()
            badge.isVisible = it > 0
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCartItemCount()
        setOnBadgeCartItem()
    }

}
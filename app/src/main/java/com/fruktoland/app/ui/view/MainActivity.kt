package com.fruktoland.app.ui.view

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.fruktoland.app.R
import com.fruktoland.app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView {
    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigation = binding.bottomNavigationMenu

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    navigateToFragmentById(R.id.mainFragment)
                }
                R.id.navigation_basket -> {
                    navigateToFragmentById(R.id.orderFragment)
                }
                else -> {
                    navigateToFragmentById(R.id.mainFragment)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun navigateToFragmentById(fragmentId: Int) {
        navController.navigate(fragmentId)
    }

    override fun onFragmentbackClick() {
        navController.popBackStack()
    }
}
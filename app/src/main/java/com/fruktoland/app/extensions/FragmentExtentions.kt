package com.fruktoland.app.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment

fun Fragment.navigate(
    resId: Int,
    bundle: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
    try {
        NavHostFragment.findNavController(this).navigate(resId, bundle, navOptions)
    } catch (ex: Throwable) {
    }
}

fun Fragment.navigate(
    navDirection: NavDirections,
    bundle: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
    try {
        NavHostFragment.findNavController(this).navigate(navDirection, navOptions)
    } catch (ex: Throwable) {
    }
}

fun Fragment.back() {
    try {
        requireActivity().onBackPressed()
    } catch (ex: Throwable) {
    }
}

fun Fragment.navigateUp() {
    NavHostFragment.findNavController(this).navigateUp()
}
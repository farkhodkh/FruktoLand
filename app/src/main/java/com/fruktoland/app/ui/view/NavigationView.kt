package com.fruktoland.app.ui.view

import android.os.Parcelable

interface NavigationView {
    fun navigateToFragmentById(fragmentId: Int)
//    fun <T: Parcelable>getDataFromPreviousBackStackEntry(dataKet: String): Parcelable?
//    fun <T: Any> putDataInCurrentBackStackEntry(dataKet: String, data: T)
    fun onFragmentbackClick()
}


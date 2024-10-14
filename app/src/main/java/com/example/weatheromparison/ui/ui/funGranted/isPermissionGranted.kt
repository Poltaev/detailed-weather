package com.example.weatheromparison.ui.ui.funGranted

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.isPermissionGranted(parameterGranted: String): Boolean {
    return ContextCompat.checkSelfPermission(
        activity as AppCompatActivity,
        parameterGranted
    ) == PackageManager.PERMISSION_GRANTED
}
package com.example.weathernow.domain.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

object Permissions {

    fun areLocationPermissionsGranted(context: Context): Boolean {
        val coarseLocationPermission = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val fineLocationPermission = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        return fineLocationPermission && coarseLocationPermission
    }

    fun requestPermission(activity: AppCompatActivity, requestId: Int) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            requestId
        )
    }

    fun isLocationEnabled(context: Context): Boolean {
        val gfgLocationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return gfgLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || gfgLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun showGPSNotEnabledDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Use Location")
            .setMessage("For a better experience, turn on location using Google's location service")
            .setCancelable(false)
            .setPositiveButton("Okay") { _, _ ->
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .show()
    }
}
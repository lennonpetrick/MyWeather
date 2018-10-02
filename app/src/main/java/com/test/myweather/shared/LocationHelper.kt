package com.test.myweather.shared

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import com.test.myweather.R



class LocationHelper(private val locationManager: LocationManager) {

    fun hasPermission(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

                && ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(activity: Activity, requestCode: Int) {
        ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                requestCode)
    }

    fun gainedPermission(grantResults: IntArray): Boolean {
        return grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    fun requestLocation(callback: (lon: Double, lat: Double) -> Unit) {
        val criteria = Criteria().apply {
            this.powerRequirement = Criteria.POWER_LOW
            this.accuracy = Criteria.ACCURACY_FINE
            this.isSpeedRequired = true
            this.isAltitudeRequired = false
            this.isBearingRequired = false
            this.isCostAllowed = false
        }

        locationManager.requestSingleUpdate(
                criteria,
                object : LocationListener {

                    override fun onLocationChanged(location: Location?) {
                        location?.let {
                            callback(it.longitude, it.latitude)
                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

                    override fun onProviderEnabled(provider: String?) {}

                    override fun onProviderDisabled(provider: String?) {}
                },
                null
        )
    }

    fun isLocationAvailable(activity: Activity, requestCode: Int): Boolean {
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)).apply {
            if (!this)
                askToEnable(activity, requestCode)
        }
    }

    private fun askToEnable(activity: Activity, requestCode: Int) {
        AlertDialog.Builder(activity)
                .setMessage(R.string.message_enable_location)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    activity.startActivityForResult(
                            Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                            requestCode
                    )
                }
                .show()
    }
}
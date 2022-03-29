package com.example.weathernow.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weathernow.R
import com.example.weathernow.domain.data.api.BaseRetrofitModule
import com.example.weathernow.domain.data.repository.WeatherNowRepositoryImplementation
import com.example.weathernow.domain.utils.Constants.LOCATION_PERMISSION_REQUEST_CODE
import com.example.weathernow.domain.utils.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import androidx.databinding.DataBindingUtil
import com.example.weathernow.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private val retrofitModule : BaseRetrofitModule by inject()
    private val weatherNowRepositoryImplementation = WeatherNowRepositoryImplementation(retrofitModule.getApiServiceService())
    private val weatherNowViewModel : WeatherNowViewModel by viewModel { parametersOf(weatherNowRepositoryImplementation)  }
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initLocation()
        observeWeather()
    }

    private fun initLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission()
    }

    private fun observeWeather() {
        weatherNowViewModel.locationWeather.observe(this) { (status, data, error) ->
            when (status) {
                Resource.Status.LOADING -> binding.loadingWeather = true
                Resource.Status.SUCCESS -> {
                    data?.let { mData ->
                        binding.weatherData = mData
                        binding.loadingWeather = false
                    }
                }
                Resource.Status.ERROR -> {
                    binding.loadingWeather = false
                    error?.let { mError ->
                        binding.error = mError
                    }
                }
            }
        }
    }

    private fun checkLocationPermission() {
        if (areLocationPermissionsGranted()) {
            initLocationUpdates()
        } else {
            requestPermission()
        }
    }

    private fun areLocationPermissionsGranted(): Boolean {
        val coarseLocationPermission = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val fineLocationPermission = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        return fineLocationPermission && coarseLocationPermission
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this@HomeActivity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE)
    }

    @SuppressLint("MissingPermission")
    private fun initLocationUpdates() {
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    // Invoke Weather Data Fetch
                    weatherNowViewModel.observeLocationWeather(it.latitude, it.longitude)
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> when {
                grantResults.isEmpty() -> { finish() }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> initLocationUpdates()
                else -> { finish() }
            }
        }
    }
}
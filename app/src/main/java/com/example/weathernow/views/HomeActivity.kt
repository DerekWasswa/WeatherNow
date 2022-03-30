package com.example.weathernow.views

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weathernow.R
import com.example.weathernow.domain.data.api.BaseRetrofitModule
import com.example.weathernow.domain.data.repository.WeatherNowRepositoryImplementation
import com.example.weathernow.domain.utils.Constants.LOCATION_PERMISSION_REQUEST_CODE
import com.example.weathernow.domain.utils.Resource
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.weathernow.databinding.ActivityHomeBinding
import com.example.weathernow.domain.utils.Constants.FASTEST_INTERVAL
import com.example.weathernow.domain.utils.Constants.UPDATE_INTERVAL
import com.example.weathernow.domain.utils.Permissions
import com.google.android.gms.location.*
import android.os.Looper
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationCallback




class HomeActivity : AppCompatActivity() {

    private val retrofitModule : BaseRetrofitModule by inject()
    private val weatherNowRepositoryImplementation = WeatherNowRepositoryImplementation(retrofitModule.getApiServiceService())
    private val weatherNowViewModel : WeatherNowViewModel by viewModel { parametersOf(weatherNowRepositoryImplementation)  }
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var mLocationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        binding.loadingWeather = true
        observeWeather()
    }

    private fun initViews() {
        viewPager = binding.vpWeather
        val pagerAdapter = WeatherSlidePagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = pagerAdapter
    }

    private fun observeWeather() {
        weatherNowViewModel.locationWeather.observe(this) { (status, data, error) ->
            when (status) {
                Resource.Status.LOADING -> {
                    binding.errorLoadingWeather = false
                    binding.loadingWeather = true
                }
                Resource.Status.SUCCESS -> {
                    data?.let { mData ->
                        binding.weatherLocation = mData.city.name
                        binding.loadingWeather = false
                        binding.errorLoadingWeather = false
                        initViews()
                        pauseLocationUpdates()
                    }
                }
                Resource.Status.ERROR -> {
                    binding.loadingWeather = false
                    binding.errorLoadingWeather = true
                    pauseLocationUpdates()
                    error?.let { mError ->
                        binding.error = mError
                    }
                }
            }
        }
    }

    private fun pauseLocationUpdates() {
        if (::fusedLocationProviderClient.isInitialized) {
            fusedLocationProviderClient.removeLocationUpdates(locationUpdatesCallback)
        }
    }

    private val locationUpdatesCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            // Invoke Weather Data Fetch
            weatherNowViewModel.observeLocationWeather(lastLocation.latitude, lastLocation.longitude)
        }
    }

    @SuppressLint("MissingPermission")
    private fun initLocationUpdates() {
        mLocationRequest = LocationRequest.create()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = UPDATE_INTERVAL
        mLocationRequest.fastestInterval = FASTEST_INTERVAL

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        Looper.myLooper()?.let { mLopper ->
            fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationUpdatesCallback, mLopper)
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
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {

                    when {
                        Permissions.isLocationEnabled(this@HomeActivity) -> {
                            initLocationUpdates()
                        }
                        else -> {
                            Permissions.showGPSNotEnabledDialog(this@HomeActivity)
                        }
                    }
                }
                else -> { finish() }
            }
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    override fun onStart() {
        super.onStart()
        when {
            Permissions.areLocationPermissionsGranted(this@HomeActivity) -> {
                when {
                    Permissions.isLocationEnabled(this@HomeActivity) -> {
                        initLocationUpdates()
                    }
                    else -> {
                        Permissions.showGPSNotEnabledDialog(this@HomeActivity)
                    }
                }
            }
            else -> {
                Permissions.requestPermission(this@HomeActivity, LOCATION_PERMISSION_REQUEST_CODE)
            }
        }
    }
}
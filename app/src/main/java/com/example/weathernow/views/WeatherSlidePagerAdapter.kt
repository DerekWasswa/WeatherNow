package com.example.weathernow.views

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weathernow.domain.utils.Constants.WEATHER_PAGES

class WeatherSlidePagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = WEATHER_PAGES
    override fun createFragment(position: Int): Fragment = WeatherSlidePageFragment.newInstance(position)

}
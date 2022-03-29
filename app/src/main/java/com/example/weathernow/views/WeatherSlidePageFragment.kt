package com.example.weathernow.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weathernow.databinding.WeatherPageBinding

class WeatherSlidePageFragment: Fragment() {

    var pagePosition: Int = 0

    companion object {
        fun newInstance(page: Int) : WeatherSlidePageFragment {
            val weatherSlidePageFragment = WeatherSlidePageFragment()
            weatherSlidePageFragment.pagePosition = page
            return weatherSlidePageFragment
        }
    }

    private var _binding: WeatherPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = WeatherPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Init Views and Set Page Data
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
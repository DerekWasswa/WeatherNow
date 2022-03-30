package com.example.weathernow.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.weathernow.R
import com.example.weathernow.databinding.WeatherPageBinding
import com.example.weathernow.domain.data.models.DailyWeather
import com.example.weathernow.domain.data.models.Weather
import com.example.weathernow.domain.data.models.sortWeatherItems
import com.example.weathernow.domain.utils.Resource
import com.example.weathernow.domain.utils.getZonedDateTime
import com.example.weathernow.domain.utils.visible
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.time.ZonedDateTime
import java.time.format.TextStyle
import java.util.*
import kotlin.math.roundToInt

class WeatherSlidePageFragment: Fragment() {

    var pagePosition: Int = 0

    private val weatherNowViewModel by sharedViewModel<WeatherNowViewModel>()

    private lateinit var adapter: WeatherAdapter

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
        initViews()
        observeWeatherData()
    }

    private fun initViews() {
        binding.rvWeatherChanges.setHasFixedSize(true)
        adapter = WeatherAdapter(requireContext())
        binding.rvWeatherChanges.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWeatherChanges.adapter = adapter
        adapter.notifyDataSetChanged()

        binding.tvWeatherTomorrow.setOnClickListener {
            val viewPager: ViewPager2 = requireActivity().findViewById(R.id.vpWeather)
            val nextPage = if (pagePosition == 4) 0 else pagePosition + 1
            viewPager.setCurrentItem(nextPage, true)
        }
    }

    private fun observeWeatherData() {
        weatherNowViewModel.locationWeather.observe(viewLifecycleOwner) { (status, data, error) ->
            when (status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    data?.let { mData ->
                        handleWeatherResponse(mData)
                    }
                }
                Resource.Status.ERROR -> {
                    error?.let {}
                }
            }
        }
    }

    private fun handleWeatherResponse(weather: Weather) {
        val filteredDailyWeather: MutableList<DailyWeather> = mutableListOf()
        val dailyWeatherList: List<DailyWeather> = weather.list
        for (dailyWeatherItem in dailyWeatherList) {
            // set fragment weather data headers
            if (dailyWeatherItem.dtTxt.getZonedDateTime().toLocalDate().equals(ZonedDateTime.now().toLocalDate().plusDays((pagePosition).toLong()))) {
                filteredDailyWeather.add(dailyWeatherItem)

                binding.tvWeatherDay.text =
                    String.format(
                        "%s",
                        ZonedDateTime.now().toLocalDateTime().plusDays((pagePosition).toLong()).dayOfWeek.getDisplayName(
                            TextStyle.FULL,
                            Locale.forLanguageTag("en")
                        )
                    )

                binding.tvWeatherTomorrow.text =
                    String.format(
                        "%s",
                        ZonedDateTime.now().toLocalDateTime()
                            .plusDays((pagePosition + 1).toLong()).dayOfWeek.getDisplayName(
                                TextStyle.FULL,
                                Locale.forLanguageTag("en")
                            )
                    )
            }
        }

        if (filteredDailyWeather.isNotEmpty()) {
            val currentDailyWeather: DailyWeather = filteredDailyWeather.sortWeatherItems()[0]

            binding.weather = currentDailyWeather

            currentDailyWeather.weather[0].main?.value?.let { mValue ->
                val weatherDescription: String = mValue
                setIcon(weatherDescription)

                binding.tvWeatherStatus.text = weatherDescription
            }

            var temp: Double = currentDailyWeather.main.temp - 273.15
            temp = (temp * 10).roundToInt() / 10.0

            binding.tvWeatherTemperature.text = String.format("%s \u00B0C %s", temp, "")

            binding.dailyWeatherList = filteredDailyWeather
            binding.groupWeather.visible()
        }

    }

    private fun setIcon(weatherCondition: String) {
        when (weatherCondition) {
            "Clouds" -> binding.ivWeatherIcon.setImageResource(R.drawable.ic_wi_cloudy_primary)
            "Clear", "Sunny" -> binding.ivWeatherIcon.setImageResource(R.drawable.ic_wi_day_sunny_primary)
            "Rain" -> binding.ivWeatherIcon.setImageResource(R.drawable.ic_wi_rain)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
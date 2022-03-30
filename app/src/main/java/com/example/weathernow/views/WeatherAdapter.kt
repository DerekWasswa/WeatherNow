package com.example.weathernow.views

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathernow.R
import com.example.weathernow.databinding.NoWeatherChangesBinding
import com.example.weathernow.databinding.WeatherItemBinding
import com.example.weathernow.domain.data.models.DailyWeather
import com.example.weathernow.domain.data.models.ViewType
import java.util.ArrayList
import kotlin.math.roundToInt

class WeatherAdapter(context: Context) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private val weatherList: MutableList<DailyWeather>
    private val context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            0 -> {
                val binding: WeatherItemBinding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                WeatherViewHolder(binding)
            }
            1 -> {
                val noNoteBinding: NoWeatherChangesBinding = NoWeatherChangesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EmptyViewHolder(noNoteBinding)
            }
            else -> {
                val noNoteBinding: NoWeatherChangesBinding = NoWeatherChangesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EmptyViewHolder(noNoteBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return if (weatherList.isEmpty()) {
            1
        } else {
            weatherList.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (weatherList.isEmpty()) {
            ViewType.EMPTY.ordinal
        } else {
            ViewType.NORMAL.ordinal
        }
    }

    // Main ViewHolder
    inner class WeatherViewHolder internal constructor(binding: WeatherItemBinding) : BaseViewHolder(binding.root) {
        private val mBinding: WeatherItemBinding = binding

        override fun onBind(position: Int) {
            val model: DailyWeather = weatherList[position]
            mBinding.weatherItem = model
            // time
            mBinding.tvWeatherTime.text = java.lang.String.format("%s", model.dtTxt)
            Log.d("wavespress", model.dtTxt)

            // temperature
            var temp: Double = model.main.temp - 273.15
            temp = (temp * 10).roundToInt() / 10.0
            mBinding.tvWeatherStatus.text = String.format("%s \u00B0C %s", temp, "")

            // appropriate icon
            val weatherStatus: String = model.weather[0].main.value
            setIcon(mBinding, weatherStatus)

            mBinding.executePendingBindings()
            if (position % 2 == 1) {
                mBinding.llWeatherChanges.setBackgroundColor(context.resources.getColor(R.color.grey))
            } else {
                mBinding.llWeatherChanges.setBackgroundColor(context.resources.getColor(R.color.white))
            }
        }

    }

    private fun setIcon(mBinding: WeatherItemBinding, weatherCondition: String) {
        when (weatherCondition) {
            "Clouds" -> mBinding.ivWeatherIcon.setImageResource(R.drawable.ic_wi_cloudy_primary)
            "Clear", "Sunny" -> mBinding.ivWeatherIcon.setImageResource(R.drawable.ic_wi_day_sunny_primary)
            "Rain" -> mBinding.ivWeatherIcon.setImageResource(R.drawable.ic_wi_rain)
        }
    }

    inner class EmptyViewHolder internal constructor(binding: NoWeatherChangesBinding) : BaseViewHolder(binding.root) {
        override fun onBind(position: Int) {}
    }

    fun addWeatherChanges(weather: List<DailyWeather>?) {
        weatherList.addAll(weather!!)
        notifyDataSetChanged()
    }

    fun clearItems() {
        weatherList.clear()
    }

    init {
        weatherList = ArrayList<DailyWeather>()
        this.context = context
    }
}

abstract class BaseViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(position: Int)
}
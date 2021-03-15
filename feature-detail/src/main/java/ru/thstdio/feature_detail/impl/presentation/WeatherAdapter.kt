package ru.thstdio.feature_detail.impl.presentation

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.thstdio.core.domain.WeatherWithDate
import ru.thstdio.core_ui_util.util.convertIdWeatherToResId
import ru.thstdio.feature_detail.R
import ru.thstdio.feature_detail.databinding.HolderWeatherBinding


class WeatherAdapter(private val onClickItem: OnWeatherItemClickListener) :
    ListAdapter<WeatherHolderItem, WeatherHolder>(WeatherDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.holder_weather, parent, false)
        val binding: HolderWeatherBinding = HolderWeatherBinding.bind(view)
        return WeatherHolder(binding, onClickItem)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        val item = getItem(position)
        holder.onBindViewHolder(item)
    }
}

class WeatherDiffUtil : DiffUtil.ItemCallback<WeatherHolderItem>() {
    override fun areItemsTheSame(
        oldItem: WeatherHolderItem,
        newItem: WeatherHolderItem
    ): Boolean = oldItem.weather.date == newItem.weather.date


    override fun areContentsTheSame(
        oldItem: WeatherHolderItem,
        newItem: WeatherHolderItem
    ): Boolean = oldItem == newItem

}

class WeatherHolder(
    private val binding: HolderWeatherBinding,
    private val onClickItem: OnWeatherItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    private var _item: WeatherHolderItem? = null

    init {
        binding.root.setOnClickListener {
            _item?.let { item ->
                onClickItem.onClickItem(item)
            }
        }
    }

    fun onBindViewHolder(item: WeatherHolderItem) {
        _item = item
        with(binding) {
            date.text = item.weather.dateTitle
            icWeather.setImageResource(convertIdWeatherToResId(item.weather.weather.iconId))
            tempMax.text = binding.tempMax.context.getString(
                R.string.temperature,
                item.weather.weather.tempMax.toString()
            )
            tempMin.text = binding.tempMin.context.getString(
                R.string.temperature,
                item.weather.weather.tempMin.toString()
            )
            animation(item.isActive)
            root.isSelected = item.isActive
        }

    }

    private fun animation(active: Boolean) {
        val valueAnimator = if (active) ValueAnimator.ofFloat(0.9F, 1F)
        else ValueAnimator.ofFloat(1F, 0.9F)
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            binding.root.scaleX = value
            binding.root.scaleY = value
        }
        valueAnimator.apply {
            interpolator = AnticipateOvershootInterpolator()
            duration = 1000
            start()
        }
    }
}

data class WeatherHolderItem(val isActive: Boolean, val weather: WeatherWithDate)
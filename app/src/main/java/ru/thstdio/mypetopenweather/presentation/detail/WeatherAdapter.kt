package ru.thstdio.mypetopenweather.presentation.detail

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.thstdio.mypetopenweather.R
import ru.thstdio.mypetopenweather.databinding.HolderWeatherBinding
import ru.thstdio.mypetopenweather.domain.WeatherWithDate
import ru.thstdio.mypetopenweather.presentation.view.util.convertIdWeatherToResId

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
    ): Boolean = oldItem == newItem


    override fun areContentsTheSame(
        oldItem: WeatherHolderItem,
        newItem: WeatherHolderItem
    ): Boolean = oldItem.weather.date == newItem.weather.date

}

class WeatherHolder(
    private val binding: HolderWeatherBinding,
    private val onClickItem: OnWeatherItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBindViewHolder(item: WeatherHolderItem) {
        binding.date.text = item.weather.dateTitle
        binding.icWeather.setImageResource(convertIdWeatherToResId(item.weather.weather.iconId))
        binding.tempMax.text = binding.tempMax.context.getString(
            R.string.temperature,
            item.weather.weather.tempMax.toString()
        )
        binding.tempMin.text = binding.tempMin.context.getString(
            R.string.temperature,
            item.weather.weather.tempMin.toString()
        )
        binding.root.setOnClickListener { onClickItem.onClickItem(item) }
        animation(item.isActive)
        binding.root.background = ContextCompat.getDrawable(
            binding.root.context,
            if (item.isActive) R.drawable.shape_active_round_square else R.drawable.shape_round_square
        )
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
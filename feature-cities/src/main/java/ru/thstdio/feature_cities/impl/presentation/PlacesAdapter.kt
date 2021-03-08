package ru.thstdio.feature_cities.impl.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.thstdio.core.domain.PlaceAndWeather
import ru.thstdio.core_ui_util.util.calcWeatherColor
import ru.thstdio.core_ui_util.util.convertIdWeatherToResId
import ru.thstdio.feature_cities.R
import ru.thstdio.feature_cities.databinding.HolderPlaceBinding

class PlacesAdapter(private val placeHolderAction: PlaceHolderAction) :
    ListAdapter<PlaceAndWeather, PlaceHolder>(PlaceDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.holder_place, parent, false)
        val binding: HolderPlaceBinding = HolderPlaceBinding.bind(view)
        return PlaceHolder(binding, placeHolderAction)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        val item = getItem(position)
        holder.onBindViewHolder(item)
    }
}

class PlaceDiffUtil : DiffUtil.ItemCallback<PlaceAndWeather>() {
    override fun areItemsTheSame(oldItem: PlaceAndWeather, newItem: PlaceAndWeather): Boolean {
        val oldPlace = oldItem.place
        val newPlace = newItem.place
        return oldPlace.cityId == newPlace.cityId
    }


    override fun areContentsTheSame(oldItem: PlaceAndWeather, newItem: PlaceAndWeather): Boolean {
        val (oldPlace, oldWeather) = oldItem
        val (newPlace, newWeather) = newItem
        return oldPlace == newPlace && oldWeather == newWeather
    }

}

class PlaceHolder(
    private val binding: HolderPlaceBinding,
    private val placeHolderAction: PlaceHolderAction
) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBindViewHolder(item: PlaceAndWeather) {
        val (place, weather) = item
        binding.name.text = place.name
        if (weather != null) {
            val temperature = weather.temperature
            binding.temperatureValue.text = binding.temperatureValue.context.getString(
                R.string.temperature,
                temperature.toString()
            )
            binding.temperatureValue.setTextColor(
                calcWeatherColor(
                    temperature,
                    binding.temperatureValue.context
                )
            )
            binding.icWeather.setImageResource(convertIdWeatherToResId(weather.iconId))
        } else {
            placeHolderAction.updateWeather(place)
        }

        binding.root.setOnClickListener { placeHolderAction.onClickPlace(place) }
    }
}
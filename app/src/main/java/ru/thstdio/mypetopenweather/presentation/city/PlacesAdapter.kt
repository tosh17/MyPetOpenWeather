package ru.thstdio.mypetopenweather.presentation.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.thstdio.mypetopenweather.R
import ru.thstdio.mypetopenweather.databinding.HolderPlaceBinding
import ru.thstdio.mypetopenweather.domain.PlaceWeather
import ru.thstdio.mypetopenweather.presentation.view.util.calcWeatherColor

class PlacesAdapter(private val placeHolderAction: PlaceHolderAction) :
    ListAdapter<PlaceWeather, PlaceHolder>(PlaceDiffUtil()) {
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

class PlaceDiffUtil : DiffUtil.ItemCallback<PlaceWeather>() {
    override fun areItemsTheSame(oldItem: PlaceWeather, newItem: PlaceWeather): Boolean {
        val oldPlace = oldItem.first
        val newPlace = newItem.first
        return oldPlace.cityId == newPlace.cityId
    }


    override fun areContentsTheSame(oldItem: PlaceWeather, newItem: PlaceWeather): Boolean {
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

    fun onBindViewHolder(item: PlaceWeather) {
        val (place, weather) = item
        binding.name.text = place.name
        if (weather != null) {
            val temperature = weather.temperature
            binding.temperatureValue.text = temperature.toString()
            binding.temperatureValue.setTextColor(
                calcWeatherColor(
                    temperature,
                    binding.temperatureValue.context
                )
            )
        } else {
            placeHolderAction.updateWeather(place)
        }

        binding.root.setOnClickListener { placeHolderAction.onClickPlace(place) }
    }
}
package ru.thstdio.mypetopenweather.presentation.city

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import ru.thstdio.mypetopenweather.R
import ru.thstdio.mypetopenweather.databinding.HolderPlaceBinding
import ru.thstdio.mypetopenweather.domain.Place
import ru.thstdio.mypetopenweather.presentation.view.util.calcWeatherColor

class PlacesAdapter(private val useCase: CityUseCase) :
    ListAdapter<Place, PlaceHolder>(PlaceDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.holder_place, parent, false)
        val binding: HolderPlaceBinding = HolderPlaceBinding.bind(view)
        return PlaceHolder(binding, useCase)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        val item = getItem(position)
        holder.onBindViewHolder(item)
    }
}

class PlaceDiffUtil : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean =
        oldItem.name == newItem.name


    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean = oldItem == newItem

}

class PlaceHolder(private val binding: HolderPlaceBinding, private val useCase: CityUseCase) :
    RecyclerView.ViewHolder(binding.root) {
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        Log.e("PlaceHolder", exception.toString())
    }
    private val scope =
        CoroutineScope(SupervisorJob() + exceptionHandler + Dispatchers.Main.immediate)

    fun onBindViewHolder(item: Place) {
        binding.name.text = item.name
        scope.launch {
            val temperature = useCase.getWeatherByPlace(place = item).temperature
            binding.temperatureValue.text = temperature.toString()
            binding.temperatureValue.setTextColor(
                calcWeatherColor(
                    temperature,
                    binding.temperatureValue.context
                )
            )
        }
    }



}
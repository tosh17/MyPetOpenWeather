package ru.thstdio.mypetopenweather.presentation.city

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.thstdio.mypetopenweather.R
import ru.thstdio.mypetopenweather.databinding.FragmentCityBinding
import ru.thstdio.mypetopenweather.domain.Place
import ru.thstdio.mypetopenweather.domain.Weather
import ru.thstdio.mypetopenweather.presentation.OpenWeatherApp
import ru.thstdio.mypetopenweather.presentation.view.util.calcWeatherColor
import javax.inject.Inject

class CityFragment : Fragment(R.layout.fragment_city) {
    companion object {
        fun newInstance() = CityFragment()
    }

    @Inject
    lateinit var useCase: CityUseCase
    private val binding: FragmentCityBinding by viewBinding()
    private val viewModel: CityViewModel by viewModels {
        CityViewModelFactory(useCase)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as OpenWeatherApp).daggerComponent.inject(this)
        binding.editTextCity.doOnTextChanged { text, _, _, _ ->
            viewModel.setCity(text.toString())
        }
        binding.editTextCity.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) viewModel.getWeather()
            true
        }
        viewModel.weather.observe(this.viewLifecycleOwner, this::updateCurrentWeather)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val adapter = PlacesAdapter(useCase)
        binding.recyclerView.adapter = adapter
        viewModel.places.observe(this.viewLifecycleOwner, adapter::submitList)
    }

    private fun updateCurrentWeather(placeAndWeather: Pair<Place, Weather>) {
        val (place, weather) = placeAndWeather
        binding.weatherCard.cityValue.text = place.name
        binding.weatherCard.temperatureValue.text =
            getString(R.string.temperature, weather.temperature.toString())
        binding.weatherCard.temperatureValue.setTextColor(
            calcWeatherColor(
                weather.temperature,
                binding.weatherCard.temperatureValue.context
            )
        )
        binding.weatherCard.feelsLike.text =
            getString(R.string.feel_like, weather.feelsLike.toString())
    }
}
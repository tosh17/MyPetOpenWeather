package ru.thstdio.mypetopenweather.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.thstdio.core.domain.Place
import ru.thstdio.mypetopenweather.R
import ru.thstdio.mypetopenweather.databinding.FragmentDetailBinding
import ru.thstdio.mypetopenweather.presentation.view.util.convertIdWeatherToResId
import ru.thstdio.mypetopenweather.presentation.view.util.winSpeedToResId
import ru.thstdio.mypetopenweather.presentation.view.util.windDirectionToResId

const val PLACE_ID_ARG = "PLACE_ID_ARG"

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    companion object {

        fun newInstance(place: Place): DetailFragment {
            val arg = Bundle()
            arg.putLong(PLACE_ID_ARG, place.cityId)
            val fragment = DetailFragment()
            fragment.arguments = arg
            return fragment
        }
    }

    private val binding: FragmentDetailBinding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initTitle()
        initItemCard()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        val adapter = WeatherAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.animation = null
        viewModel.weathers.observe(this.viewLifecycleOwner, adapter::submitList)
    }

    private fun initItemCard() {
        viewModel.selectedWeather.observe(this.viewLifecycleOwner) { item ->
            val weather = item.weather.weather
            binding.date.text = item.weather.dateTitle
            binding.temperatureText.text =
                getString(R.string.temperature, weather.temperature.toString())
            binding.icWeather.setImageResource(convertIdWeatherToResId(weather.iconId))
            binding.humidityText.text = getString(R.string.humidity, weather.humidity)
            binding.tempMax.text =
                getString(R.string.temperature, weather.tempMax.toString())
            binding.tempMin.text =
                getString(R.string.temperature, weather.tempMin.toString())
            binding.windSpeed.text = getString(winSpeedToResId(weather.windSpeed))
            binding.windDirection.text = getString(windDirectionToResId(weather.wendDeg))
            binding.feelsLike.text =
                getString(R.string.feel_like, weather.feelsLike.toString())
        }
    }

    private fun initTitle() {
        viewModel.placeName.observe(this.viewLifecycleOwner) { city ->
            city?.let { name ->
                binding.cityName.text = name
            }
        }
    }
}
package ru.thstdio.feature_cities.impl.presentation

import android.Manifest
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.LinearInterpolator
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import ru.thstdio.core.domain.PlaceAndWeather
import ru.thstdio.core_ui_util.util.addItemDecorationWithoutLastDivider
import ru.thstdio.core_ui_util.util.calcWeatherColor
import ru.thstdio.core_ui_util.util.convertIdWeatherToResId
import ru.thstdio.feature_cities.R
import ru.thstdio.feature_cities.databinding.FragmentCityBinding
import ru.thstdio.feature_cities.impl.framework.di.CitiesFeatureComponentHolder
import javax.inject.Inject


private const val LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
private const val REQUEST_CODE: Int = 1002
private const val ANIMATION_DURATION = 2000L

class CityFragment : Fragment(R.layout.fragment_city) {
    companion object {
        fun newInstance() = CityFragment()
    }

    private val binding: FragmentCityBinding by viewBinding(FragmentCityBinding::bind)
    lateinit var viewModel: CityViewModel

    @Inject
    lateinit var viewModelFactory: CityViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CitiesFeatureComponentHolder.getComponent().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CityViewModel::class.java)

        initSearchView()
        initRecyclerView()
        initWeatherCard()
        binding.myLocationBn.setOnClickListener { checkLocationPermission() }

    }

    private fun initWeatherCard() {
        viewModel.weatherCard.observe(this.viewLifecycleOwner, this::updateCurrentWeather)
        binding.weatherCard.btnToMap.setOnClickListener {
            viewModel.onClickToMap()
        }
        binding.weatherCard.btnToDetail.setOnClickListener {
            viewModel.onClickToDetail()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.addItemDecorationWithoutLastDivider()
        val adapter = PlacesAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.animation = null
        viewModel.places.observe(this.viewLifecycleOwner, adapter::submitList)

    }

    private fun initSearchView() {
        binding.editTextCity.doOnTextChanged { text, _, _, _ ->
            viewModel.setCity(text.toString())
        }
        binding.editTextCity.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) viewModel.findCityWeather()
            binding.editTextCity.setText("")
            hideKeyboard()
            true
        }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                LOCATION_PERMISSION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(LOCATION_PERMISSION), REQUEST_CODE)
        } else {
            getLastLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                viewModel.updateCurrentLocation(location)
            } else {
                getCurrentLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5 * 1000
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation.let(viewModel::updateCurrentLocation)
            }
        }

        fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun updateCurrentWeather(placeAndWeather: PlaceAndWeather) {
        val (place, weather) = placeAndWeather
        weather?.let { weather ->
            val colorTemperature = calcWeatherColor(
                weather.temperature,
                binding.weatherCard.temperatureValue.context
            )
            animationWeatherCard()
            animationBackGround(colorTemperature)

            binding.root.postDelayed({
                binding.root.setBackgroundColor(
                    calcWeatherColor(
                        weather.temperature,
                        binding.weatherCard.temperatureValue.context
                    )
                )
                binding.weatherCard.weatherIcon.setImageResource(convertIdWeatherToResId(weather.iconId))
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
            }, getAnimationTime() / 2)
            viewModel.weatherCardUpdated()
        }
    }

    private fun animationWeatherCard() {
        val valueAnimator = ValueAnimator.ofFloat(1f, 0F, 1F)
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            binding.weatherCardView.scaleX = value
            binding.weatherCardView.scaleY = value
        }
        valueAnimator.apply {
            interpolator = AnticipateOvershootInterpolator()
            repeatMode = ValueAnimator.REVERSE
            //repeatCount = 2
            duration = getAnimationTime()
            start()
        }
    }

    private fun animationBackGround(newColor: Int) {
        val colorCurrent = (binding.root.background as ColorDrawable).color
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorCurrent, newColor)
        colorAnimation.addUpdateListener {
            val value = it.animatedValue as Int
            binding.root.setBackgroundColor(value)
        }
        colorAnimation.apply {
            interpolator = LinearInterpolator()
            duration = getAnimationTime()
            start()
        }
    }

    private fun getAnimationTime() =
        if (viewModel.weatherCardWithAnimation) ANIMATION_DURATION else 0

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (permissions.first() == LOCATION_PERMISSION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                getLastLocation()
            }
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextCity.windowToken, 0)
    }
}
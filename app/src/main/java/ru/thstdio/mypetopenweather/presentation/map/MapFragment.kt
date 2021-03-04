package ru.thstdio.mypetopenweather.presentation.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import ru.thstdio.mypetopenweather.R
import ru.thstdio.mypetopenweather.data.convertor.toGoogleMarker
import ru.thstdio.mypetopenweather.databinding.FragmentMapBinding
import ru.thstdio.mypetopenweather.domain.Marker
import ru.thstdio.mypetopenweather.domain.Place
import ru.thstdio.mypetopenweather.domain.PlaceAndWeather
import ru.thstdio.mypetopenweather.presentation.view.util.convertIdWeatherToResId


@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {
    companion object {
        private const val PLACE_LAT_ARG = "PLACE_LAT_ARG"
        private const val PLACE_LON_ARG = "PLACE_LON_ARG"
        fun newInstance(place: Place): MapFragment {
            val arg = Bundle()
            arg.putDouble(PLACE_LAT_ARG, place.location.latitude)
            arg.putDouble(PLACE_LON_ARG, place.location.longitude)
            val fragment = MapFragment()
            fragment.arguments = arg
            return fragment
        }
    }


    private val bindin by viewBinding(FragmentMapBinding::bind)
    private val viewModel: MapViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let { map ->
            viewModel.places.observe(this.viewLifecycleOwner, { list ->
                createMarkers(map, list)
            })
        }
        arguments?.let { arg ->
            val currentPosition = LatLng(arg.getDouble(PLACE_LAT_ARG), arg.getDouble(PLACE_LON_ARG))
            googleMap?.moveCamera(CameraUpdateFactory.newLatLng(currentPosition))
        }

    }

    private fun createMarkers(map: GoogleMap, list: List<PlaceAndWeather>) {
        list.forEach { (place, weather) ->
            createMarker(
                map,
                Marker(
                    place.location,
                    place.name,
                    weather!!.temperature.toString(),
                    weather.iconId
                )
            )
        }
    }

    private fun createMarker(googleMap: GoogleMap, marker: Marker) {
        googleMap.addMarker(
            marker.toGoogleMarker()
                .icon(createBitmapDescriptor(marker.idWeatherIcon))
        )
    }


    private fun createBitmapDescriptor(id: Int): BitmapDescriptor {
        val bitmapDraw = ContextCompat.getDrawable(
            requireContext(),
            convertIdWeatherToResId(id)
        ) as BitmapDrawable
        val b = bitmapDraw.bitmap
        val height = convertDpToPx(requireContext(), 33f)
        val width = convertDpToPx(requireContext(), 48f)
        val smallMarker =
            Bitmap.createScaledBitmap(b, width, height, false)
        return BitmapDescriptorFactory.fromBitmap(smallMarker)
    }

    private fun convertDpToPx(context: Context, dp: Float): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}
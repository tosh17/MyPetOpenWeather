package ru.thstdio.feature_map.impl.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import ru.thstdio.core.domain.Marker
import ru.thstdio.core.domain.Place
import ru.thstdio.core.domain.PlaceAndWeather
import ru.thstdio.core_ui_util.util.convertIdWeatherToResId
import ru.thstdio.feature_map.R
import ru.thstdio.feature_map.impl.data.toGoogleMarker
import ru.thstdio.feature_map.impl.framework.di.MapFeatureComponentHolder
import javax.inject.Inject

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

    @Inject
    lateinit var viewModelFactory: MapViewModelFactory
    private val viewModel: MapViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MapViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MapFeatureComponentHolder.getComponent().inject(this)
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
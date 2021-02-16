package ru.thstdio.mypetopenweather.data.convertor

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.thstdio.mypetopenweather.domain.Marker

fun Marker.toGoogleMarker(): MarkerOptions {
    val position = LatLng(this.location.latitude, this.location.longitude)
    return MarkerOptions()
        .position(position)
        .title(this.title)
        .snippet(this.snippet)
}
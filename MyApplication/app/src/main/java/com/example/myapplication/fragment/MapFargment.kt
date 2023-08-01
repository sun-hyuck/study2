package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFargment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_map,container,false)
            mapView = rootView.findViewById(R.id.mapFragment) as MapView
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync(this)
        return rootView
    }

    override fun onMapReady(map: GoogleMap) {
        val point = LatLng(37.514655, 125.979974)
        map.addMarker(MarkerOptions().position(point).title("현위치"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(point,12f))
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop(){
        super.onStop()
        mapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}

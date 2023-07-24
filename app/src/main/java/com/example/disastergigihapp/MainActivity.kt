package com.example.disastergigihapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.disastergigihapp.databinding.ActivityMainBinding
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.scalebar.scalebar

var mapView: MapView? = null

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapView = binding.mapView
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)
        mapView?.scalebar?.enabled = false
    }
}
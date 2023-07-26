package com.example.disastergigihapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.disastergigihapp.data.DisasterAdapter
import com.example.disastergigihapp.data.remote.DisasterResponse
import com.example.disastergigihapp.data.remote.GeometriesItem
import com.example.disastergigihapp.data.remote.RetrofitClient
import com.example.disastergigihapp.databinding.ActivityMainBinding
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.scalebar.scalebar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var mapView: MapView? = null
    private lateinit var mainBinding: ActivityMainBinding
    private val list = ArrayList<GeometriesItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val rvPost = findViewById<RecyclerView>(R.id.rvPost)
        rvPost.setHasFixedSize(true)
        rvPost.layoutManager = LinearLayoutManager(this)

        loadMap()
        getReport(rvPost)
    }

    private fun getReport(rvPost: RecyclerView) {
        RetrofitClient.instance.getStatus().enqueue(object: Callback<DisasterResponse> {
            override fun onResponse(
                call: Call<DisasterResponse>,
                response: Response<DisasterResponse>
            ) {
                Log.i("CHECK_RESPONSE", "${response.body()?.statusCode}")
                response.body()?.result?.objects?.output?.geometries?.let { list.addAll(it) }
                val adapter = DisasterAdapter(list)
                rvPost.adapter = adapter
            }

            override fun onFailure(call: Call<DisasterResponse>, t: Throwable) {
                Log.i("CHECK_RESPONSE", "${t.message}")
            }

        })
    }

    private fun loadMap() {
        mapView = mainBinding.mapView
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)
        mapView?.scalebar?.enabled = false
    }
}
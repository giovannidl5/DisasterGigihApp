package com.example.disastergigihapp.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.disastergigihapp.R
import com.example.disastergigihapp.presentation.recyclerview.DisasterAdapter
import com.example.disastergigihapp.data.remote.DisasterResponse
import com.example.disastergigihapp.data.remote.GeometriesItem
import com.example.disastergigihapp.data.remote.RetrofitClient
import com.example.disastergigihapp.databinding.ActivityMainBinding
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
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

    private fun loadMap() {
        //initialize map view, set the style, and hide the scale bar
        mapView = mainBinding.mapView
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)
        mapView?.scalebar?.enabled = false
    }

    private fun getReport(rvPost: RecyclerView) {
        //retrofit to get the data from petabencana.id
        RetrofitClient.instance.getRecent(604800).enqueue(object: Callback<DisasterResponse> {
            override fun onResponse(
                call: Call<DisasterResponse>,
                response: Response<DisasterResponse>
            ) {
                //set a variable for lesser boilerplate code
                val disasters = response.body()?.result?.objects?.output?.geometries
                disasters?.let { list.addAll(it) }

                //add the disaster points to map
                list.forEachIndexed { index, _ -> addAnnotationToMap(disasters!![index].coordinates!![0], disasters[index].coordinates!![1]) }

                //show the data in the recycler view in bottom sheet
                val adapter = DisasterAdapter(list)
                rvPost.adapter = adapter
            }

            override fun onFailure(call: Call<DisasterResponse>, t: Throwable) {
                //not handled yet
                Log.i("CHECK_RESPONSE", "${t.message}")
            }
        })
    }

    private fun addAnnotationToMap(Lng: Double, Lat: Double) {
        //documentation by mapbox
        // Create an instance of the Annotation API and get the PointAnnotationManager.
        bitmapFromDrawableRes(
            this@MainActivity,
            R.drawable.baseline_place_24
        )?.let {
            val annotationApi = mapView?.annotations
            val pointAnnotationManager = annotationApi?.createPointAnnotationManager()
            // Set options for the resulting symbol layer.
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                // Define a geographic coordinate.
                .withPoint(Point.fromLngLat(Lng, Lat))
                // Specify the bitmap you assigned to the point annotation
                // The bitmap will be added to map style automatically.
                .withIconImage(it)
            // Add the resulting pointAnnotation to the map.
            pointAnnotationManager?.create(pointAnnotationOptions)
        }
    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            // copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

}
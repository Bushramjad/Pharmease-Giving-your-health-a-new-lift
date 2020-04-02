package com.example.pharmease

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker


class infoWindowAdaptar : GoogleMap.InfoWindowAdapter {
    var context : Context? = null


    fun MarkerInfoWindowAdapter(context: Context) {
        this.context = context.applicationContext
    }

    override fun getInfoContents(p0: Marker?): View {
        val inflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflater.inflate(R.layout.info_window, null)

        val latLng: LatLng? = p0?.position
        val tvLat = v.findViewById<View>(R.id.tv_lat) as TextView
        val tvLng = v.findViewById<View>(R.id.tv_lng) as TextView
        if (latLng != null) {
            tvLat.text = "Latitude:" + latLng.latitude
        }
        if (latLng != null) {
            tvLng.text = "Longitude:" + latLng.longitude
        }
        return v    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}
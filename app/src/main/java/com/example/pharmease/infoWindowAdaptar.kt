package com.example.pharmease

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.info_window.view.*


class infoWindowAdaptar(val context: Context) : GoogleMap.InfoWindowAdapter {


    override fun getInfoContents(p0: Marker?): View {
        var mInfoView = (context as Activity).layoutInflater.inflate(R.layout.info_window, null)
        var mInfoWindow: InfoWindowData? = p0?.tag as InfoWindowData?

        mInfoView.t1.text = mInfoWindow?.text1
        mInfoView.t2.text = mInfoWindow?.text2



        return mInfoView
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}
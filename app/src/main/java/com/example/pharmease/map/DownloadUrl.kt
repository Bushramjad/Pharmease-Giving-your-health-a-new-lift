package com.example.pharmease.map

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DownloadUrl {
    @Throws(IOException::class)

    fun readUrl(strUrl: String?): String {

        var data : String = ""
        var iStream: InputStream? = null
        var urlConnection: HttpURLConnection? = null

        try {
            val url = URL(strUrl)
            urlConnection = url.openConnection() as HttpURLConnection // Creating an http connection to communicate with url
            urlConnection.connect() // Connecting to url
            iStream = urlConnection.inputStream // Reading data from url

            val br = BufferedReader(InputStreamReader(iStream))
            val sb = StringBuffer()
            var line: String?

            while (br.readLine().also { line = it } != null) {
                sb.append(line)
            }

            data = sb.toString()
            Log.d("downloadUrl", data)
            br.close()

        } catch (e: Exception) {
            Log.d("Exception", e.toString())
        } finally {
            iStream!!.close()
            urlConnection!!.disconnect()
        }
        return data
    }
}
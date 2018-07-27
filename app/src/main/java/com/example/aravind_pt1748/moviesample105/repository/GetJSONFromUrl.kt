package com.example.aravind_pt1748.moviesample105.repository

import android.os.AsyncTask
import android.text.Html
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


fun loadDataAsync(param : String): String {
    var value = ""
    try {
        val url = URL(param)
        val httpConnection = url.openConnection() as HttpURLConnection
        val inputStream = httpConnection.inputStream
        val bufferedReader = InputStreamReader(inputStream)
        value = inputStream.bufferedReader().use(BufferedReader::readText)
        Log.d("Async", " Obtained from URL : $value")
        bufferedReader.close()
    } catch (e: MalformedURLException) {
        Log.d("Async", e.toString())
    } catch (e: IOException) {
        Log.d("Async", e.toString() + "IO")
    }
    return value
}

fun htmlToString(result : String) : String{
    return Html.fromHtml(result, Html.FROM_HTML_MODE_LEGACY).toString()
}

class GetJSONFromUrl : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg params: String?): String {
        return loadDataAsync(param = params[0]!!)
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Log.d("Async","Returns : $result")
    }

}
package com.example.aravind_pt1748.moviesample105.repository

import android.content.Context
import android.util.Log
import com.example.aravind_pt1748.moviesample105.data.DBHelper
import com.example.aravind_pt1748.moviesample105.interfaces.OnPreviewDataReadyCallback
import com.example.aravind_pt1748.moviesample105.uimodel.MoviePreview
import com.example.aravind_pt1748.moviesample105.util.NetManager

class MoviePreviewRepository(private val context : Context) {

    val netManager = NetManager(context.applicationContext)

    val moviePreviewDetailsFromUrl = MoviePreviewDetailsFromUrl()

    val dbHelper = DBHelper(context = context)

    fun getMoviePreviews(onDataReadyCallback: OnPreviewDataReadyCallback) {

        val repoReady = object : OnPreviewDataReadyCallback {
            override fun onDataReady(data: MutableList<MoviePreview>) {
                dbHelper.addMoviePreviews(data)
                onDataReadyCallback.onDataReady(data)
            }
        }

        netManager.isConnectedToInternet!!.let {
            if (it) {
                moviePreviewDetailsFromUrl.getMoviePreviews(repoReady)
            }
            else {
                try {
                    onDataReadyCallback.onDataReady(dbHelper.getAllMoviePreviews())
                }
                catch (e:IllegalStateException){
                    Log.d("IllegalState",e.toString())
                }
            }
        }
    }

}
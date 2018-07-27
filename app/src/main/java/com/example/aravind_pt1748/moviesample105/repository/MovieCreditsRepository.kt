package com.example.aravind_pt1748.moviesample105.repository

import android.content.Context
import android.util.Log
import com.example.aravind_pt1748.moviesample105.data.DBHelper
import com.example.aravind_pt1748.moviesample105.interfaces.OnCreditsDataFromUrlReadyCallback
import com.example.aravind_pt1748.moviesample105.uimodel.MovieCredits
import com.example.aravind_pt1748.moviesample105.util.NetManager

class MovieCreditsRepository(private val context : Context) {

    val netManager = NetManager(context.applicationContext)

    val dbHelper = DBHelper(context = context)

    fun getMovieCredits(onDataReadyCallback: OnCreditsDataFromUrlReadyCallback,id : Int) {

        val repoReady = object : OnCreditsDataFromUrlReadyCallback {
            override fun onDataReady(data: MovieCredits) {
                dbHelper.addMovieCredits(data)
                onDataReadyCallback.onDataReady(data)
            }
        }

        netManager.isConnectedToInternet!!.let {
            if (it) {
                MovieCreditsFromUrl().getMovieCredits(repoReady,id)
            }
            else {
                Log.d("CreditsAsync","No internet , so data cannot be loaded")
                //onDataReadyCallback.onDataReady(dbHelper.getMovieCredits(id))
            }
        }
    }

}
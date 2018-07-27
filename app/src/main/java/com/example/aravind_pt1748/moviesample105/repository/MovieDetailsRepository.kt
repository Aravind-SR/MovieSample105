package com.example.aravind_pt1748.moviesample105.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.aravind_pt1748.moviesample105.data.DBHelper
import com.example.aravind_pt1748.moviesample105.interfaces.OnDetailsDataFromUrlReadyCallback
import com.example.aravind_pt1748.moviesample105.uimodel.MovieDetails
import com.example.aravind_pt1748.moviesample105.util.NetManager

class MovieDetailsRepository(private val context : Context) {

    val netManager = NetManager(context.applicationContext)

    val dbHelper = DBHelper(context = context)

    fun getMovieDetails(onDataReadyCallback: OnDetailsDataFromUrlReadyCallback, id : Int) {

        val repoReady = object : OnDetailsDataFromUrlReadyCallback {
            override fun onDataReady(data: MovieDetails) {
                dbHelper.addMovieDetails(data)
                onDataReadyCallback.onDataReady(data)
            }

        }

        netManager.isConnectedToInternet!!.let {
            if (it) {
                MovieDetailsFromUrl().getMovieDetails(repoReady,id)
            }
            else {
                Log.d("DetailsAsync","No internet , so data cannot be loaded")
                    //onDataReadyCallback.onDataReady(dbHelper.getMovieDetails(id))
                Toast.makeText(context,"Please check your internet connection",Toast.LENGTH_SHORT).show()
            }
        }
    }

}
package com.example.aravind_pt1748.moviesample105.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import android.util.Log
import com.example.aravind_pt1748.moviesample105.interfaces.OnCreditsDataFromUrlReadyCallback
import com.example.aravind_pt1748.moviesample105.interfaces.OnDetailsDataFromUrlReadyCallback
import com.example.aravind_pt1748.moviesample105.repository.MovieCreditsFromUrl
import com.example.aravind_pt1748.moviesample105.repository.MovieCreditsRepository
import com.example.aravind_pt1748.moviesample105.repository.MovieDetailsFromUrl
import com.example.aravind_pt1748.moviesample105.repository.MovieDetailsRepository
import com.example.aravind_pt1748.moviesample105.uimodel.MovieCredits
import com.example.aravind_pt1748.moviesample105.uimodel.MovieDetails
import com.example.aravind_pt1748.moviesample105.util.PersonListToJSON

class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    val isLoading = ObservableBoolean(true)

    val movieCreditsRepository = MovieCreditsRepository(application.applicationContext)

    val movieDetailsRepository = MovieDetailsRepository(application.applicationContext)

    var movieDetails : MutableLiveData<MovieDetails> = MutableLiveData()

    var movieCredits : MutableLiveData<MovieCredits> = MutableLiveData()

    val detailDataLoadCallbackInstance = object : OnDetailsDataFromUrlReadyCallback {
        override fun onDataReady(data: MovieDetails) {
            isLoading.set(false)
            movieDetails.value = data
        }
    }

    val creditsDataLoadCallbackInstance = object : OnCreditsDataFromUrlReadyCallback {
        override fun onDataReady(data: MovieCredits) {
            movieCredits.value = data
            Log.d("JSONPerson", PersonListToJSON(data.crew))
        }
    }

    fun loadMovieDetails(id : Int) {
        movieDetails.value = MovieDetails()
        movieCredits.value = MovieCredits()
        movieDetailsRepository.getMovieDetails(detailDataLoadCallbackInstance,id)
        movieCreditsRepository.getMovieCredits(creditsDataLoadCallbackInstance,id)
    }
}
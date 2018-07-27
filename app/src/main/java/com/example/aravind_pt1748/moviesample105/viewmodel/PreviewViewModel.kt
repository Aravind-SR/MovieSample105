package com.example.aravind_pt1748.moviesample105.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.databinding.ObservableField
import android.util.Log
import com.example.aravind_pt1748.moviesample105.interfaces.OnBottomReachedListener
import com.example.aravind_pt1748.moviesample105.interfaces.OnItemClickListener
import com.example.aravind_pt1748.moviesample105.interfaces.OnPreviewDataReadyCallback
import com.example.aravind_pt1748.moviesample105.repository.MoviePreviewDetailsFromUrl
import com.example.aravind_pt1748.moviesample105.repository.MoviePreviewRepository
import com.example.aravind_pt1748.moviesample105.uimodel.MoviePreview
import com.example.aravind_pt1748.moviesample105.util.NetManager
import com.example.aravind_pt1748.moviesample105.view.DetailsActivity

class PreviewViewModel(application: Application) : AndroidViewModel(application) {

    val movieRepository = MoviePreviewRepository(application.applicationContext)

    val isLoading = ObservableField(true)

    var repositories = MutableLiveData<MutableList<MoviePreview>>()

    var count = 1

    val dataLoadCallback = object : OnPreviewDataReadyCallback {
        override fun onDataReady(data: MutableList<MoviePreview>) {
            isLoading.set(false)
            if(repositories.value==null) {
                repositories.value = data
            }
            else{
                val list : MutableList<MoviePreview> = mutableListOf()
                list.addAll(repositories.value!!)
                list.addAll(data)
                repositories.value = list
            }
        }
    }

    val onItemClickInstance : OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(position: Int) {
            val id = repositories.value!![position].movieId
            Log.d("MainActivity","onItemClick() implementation in MainActivity has been invoked $id}")
            val detailIntent = Intent(application.baseContext,DetailsActivity::class.java)
            detailIntent.putExtra("id",id)
            //detailIntent.putExtra("movie",viewModel.repositories.value!![position])
            application.baseContext.startActivity(detailIntent)
        }
    }

    val onBottomReachedInstance : OnBottomReachedListener = object : OnBottomReachedListener{
        override fun onBottomReached(position: Int) {
            if(count<13) {
                loadRepositories()
                count++
            }
        }
    }

    fun loadRepositories() {
        movieRepository.getMoviePreviews(dataLoadCallback)
    }

}
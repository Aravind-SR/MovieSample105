package com.example.aravind_pt1748.moviesample105.repository

import android.os.Handler
import android.util.Log
import com.example.aravind_pt1748.moviesample105.interfaces.OnPreviewDataFromUrlReadyCallback
import com.example.aravind_pt1748.moviesample105.interfaces.OnPreviewDataReadyCallback
import com.example.aravind_pt1748.moviesample105.uimodel.MoviePreview
import com.example.aravind_pt1748.moviesample105.util.convertDate
import org.json.JSONObject

class MoviePreviewDetailsFromUrl() {

    companion object {
        const val IMG_PREFIX_PATH = "https://image.tmdb.org/t/p/original"
        const val URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=afbcda01b3f535626b2d40af1df99e31&language=en-US&page="
    }

    var count = 0

    fun parseJSONToMoviePreview(result : String?) : MutableList<MoviePreview>{
        Log.d("ParseMovieJSON", "ParseMovieJSON : argument : $result")

        val moviePreviewList : MutableList<MoviePreview> = mutableListOf()
        val jsonObject_Level1 = JSONObject(result)
        val jsonArray = jsonObject_Level1.getJSONArray("results")
        var i = 0
        while (i < jsonArray.length()) {
            val movie = jsonArray.getJSONObject(i)

            val id = movie.getInt("id")

            val title = htmlToString( movie.getString("title") )

            val date = htmlToString( movie.getString("release_date") )

            val releaseDate = convertDate(date)

            val posterPath = IMG_PREFIX_PATH + htmlToString( movie.getString("poster_path") )

            val film = MoviePreview( id, title, releaseDate, posterPath)

            Log.d("ParseMovieJSON", "Film : $film")
            moviePreviewList.add(film)
            i++
        }
        Log.d("Async", "parseJSONTF : $moviePreviewList")
        return moviePreviewList
    }

    fun previewDataInAsync() : MutableList<MoviePreview>{
        val result = GetJSONFromUrl().execute(URL + "${++count}").get()
        return parseJSONToMoviePreview(result)
    }

    fun getMoviePreviews(onDataReadyCallbackInstance : OnPreviewDataReadyCallback) {
        val onRemoteDataReadyCallbackInstance : OnPreviewDataFromUrlReadyCallback = object : OnPreviewDataFromUrlReadyCallback {
            override fun onDataReady(data: MutableList<MoviePreview>) {
                onDataReadyCallbackInstance.onDataReady(data)
            }
        }
        Handler().postDelayed({onRemoteDataReadyCallbackInstance.onDataReady(previewDataInAsync())},100)
    }
}
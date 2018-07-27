package com.example.aravind_pt1748.moviesample105.repository

import android.os.Handler
import android.util.Log
import com.example.aravind_pt1748.moviesample105.interfaces.OnDetailsDataFromUrlReadyCallback
import com.example.aravind_pt1748.moviesample105.uimodel.MovieDetails
import com.example.aravind_pt1748.moviesample105.uimodel.MoviePreview
import com.example.aravind_pt1748.moviesample105.util.convertDate
import org.json.JSONException
import org.json.JSONObject

class MovieDetailsFromUrl {

    companion object {
        const val IMG_PREFIX_PATH = "https://image.tmdb.org/t/p/original"
        const val URL_PREFIX = "https://api.themoviedb.org/3/movie/"
        const val URL_SUFFIX = "?api_key=afbcda01b3f535626b2d40af1df99e31&language=en-US"
    }

    fun parseMovieDetailsJSON(result : String?) : MovieDetails {
        Log.d("ParseMovieJSON", "ParseMovieJSON : argument : $result")

        val movie = JSONObject(result)

        val id = movie.getInt("id")

        val voteCount = movie.getInt("vote_count")

        val voteAverage = movie.getDouble("vote_average")

        val title = htmlToString( movie.getString("title") )

        val popularity =  movie.getDouble("popularity")

        val language = htmlToString( movie.getString("original_language") )

        val overview = htmlToString( movie.getString("overview") )

        val date = htmlToString( movie.getString("release_date") )

        val releaseDate = convertDate(date)

        val posterPath = IMG_PREFIX_PATH + htmlToString( movie.getString("poster_path") )

        val backdropPath = IMG_PREFIX_PATH + htmlToString( movie.getString("backdrop_path") )

        val genreList = movie.getJSONArray("genres")
        var genreObject : JSONObject = JSONObject()
        var genre : String = ""
        var i = 0
        while(i<genreList.length()){
            if(i!=0){
                genre+=" - "
            }
            genreObject = genreList.getJSONObject(i++)
            genre += genreObject.getString("name")
        }

        var runTime = 0
        try {
            runTime = movie.getInt("runtime")
        }
        catch(e:JSONException){
            Log.d("JSON",e.toString())
        }

        val film = MovieDetails(movieId = id, movieTitle = title, voteCount = voteCount, voteAverage = voteAverage, popularity = popularity, language = language, overview = overview, releaseDate = releaseDate, posterPath = posterPath, backdropPath = backdropPath, genres = genre,runTime = runTime)

        Log.d("ParseMovieDetailsJSON", "Film : $film")

        return film
    }

    fun detailDataInAsync(movieId : Int) : MovieDetails{
        val result = GetJSONFromUrl().execute(URL_PREFIX+movieId+ URL_SUFFIX).get()
        return parseMovieDetailsJSON(result)
    }

    fun getMovieDetails(onDataReadyCallbackInstance : OnDetailsDataFromUrlReadyCallback, param : Int) {
        onDataReadyCallbackInstance.onDataReady(detailDataInAsync(param))
    }

}
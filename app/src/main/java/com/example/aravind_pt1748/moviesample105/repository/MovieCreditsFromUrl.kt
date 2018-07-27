package com.example.aravind_pt1748.moviesample105.repository

import android.os.Handler
import android.util.Log
import com.example.aravind_pt1748.moviesample105.interfaces.OnCreditsDataFromUrlReadyCallback
import com.example.aravind_pt1748.moviesample105.uimodel.MovieCredits
import com.example.aravind_pt1748.moviesample105.uimodel.Person
import org.json.JSONObject

class MovieCreditsFromUrl {

    companion object {
        const val IMG_PREFIX_PATH = "https://image.tmdb.org/t/p/original"
        const val URL_PREFIX = "https://api.themoviedb.org/3/movie/"
        const val URL_SUFFIX = "/credits?api_key=afbcda01b3f535626b2d40af1df99e31"
    }

    fun parseMovieCredits(result: String?): MovieCredits {
        Log.d("ParseMovieCredits", "ParseMovieCreditsJSON : argument : $result")

        val credits = JSONObject(result)

        val id = credits.getInt("id")

        val castPersonList = mutableListOf<Person>()

        val castList = credits.getJSONArray("cast")
        var i = 0
        while (i < castList.length() && i < 10) {
            val castObject = castList.getJSONObject(i++)
            castPersonList.add(Person(castObject.getString("name"), IMG_PREFIX_PATH+castObject.getString("profile_path"), castObject.getString("character")))
        }

        val movieCredits = MovieCredits(movieId = id, crew = castPersonList)
        Log.d("Crew", "Result : $movieCredits")
        return movieCredits
    }

    fun creditsDataInAsync(movieId: Int): MovieCredits {
        val result = GetJSONFromUrl().execute(URL_PREFIX + movieId + URL_SUFFIX).get()
        return parseMovieCredits(result)
    }

    fun getMovieCredits(onDataReadyCallbackInstance : OnCreditsDataFromUrlReadyCallback, param : Int) {
        Handler().postDelayed({onDataReadyCallbackInstance.onDataReady(creditsDataInAsync(param))},100)
    }
}
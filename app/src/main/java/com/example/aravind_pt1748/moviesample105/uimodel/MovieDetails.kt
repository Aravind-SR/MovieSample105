package com.example.aravind_pt1748.moviesample105.uimodel

data class MovieDetails(
        var movieId : Int = 0,
        val movieTitle : String = "",
        val releaseDate : String = "",
        val posterPath : String = "",
        val language : String = "",
        val overview : String = "",
        val voteAverage : Double = 0.0,
        val popularity :Double = 0.0,
        val voteCount : Int = 0,
        val backdropPath : String = "",
        val genres : String = "",
        val runTime : Int = 0
)
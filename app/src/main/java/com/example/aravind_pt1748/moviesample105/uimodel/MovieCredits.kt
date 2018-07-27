package com.example.aravind_pt1748.moviesample105.uimodel

data class MovieCredits(
        var movieId : Int = 0,
        var crew : MutableList<Person> = mutableListOf()
)
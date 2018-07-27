package com.example.aravind_pt1748.moviesample105.util

import android.util.Log
import com.example.aravind_pt1748.moviesample105.uimodel.Person
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

fun PersonListToJSON(data : MutableList<Person>) : String{
    val gson = Gson()
    val string = gson.toJson(data)
    Log.d("JSONPerson","$string")
    return string
}

fun JSONToPersonList(result : String) : MutableList<Person>{
    val castList = mutableListOf<Person>()
    val creditsCrew = JSONArray(result)
    var i = 0
    while(i<creditsCrew.length()){
        val cast = creditsCrew.getJSONObject(i++)
        val character = cast.getString("character")
        val name = cast.getString("name")
        val posterPath = cast.getString("posterPath")
        castList.add(Person(name = name,character = character,posterPath = posterPath))
    }
    Log.d("JSONPerson","As mutable list of PErson : $castList")
    return castList
}


/*string+="{" +
            "\"cast\" : [" +
            "{"
    var i = 0
    while(i<data.size) {
        if(i!=0){
            string+="}," +
                    "{"
        }
        string+="\"name\" : "
        string+="\"${data[i].name}\","
        string+="\"profile_path\" : "
        string+="\"${data[i].posterPath}\","
        string+="\"character\" : "
        string+="\"${data[i++].character}\""
    }
    string+="}" +
            "]" +
            "}" */
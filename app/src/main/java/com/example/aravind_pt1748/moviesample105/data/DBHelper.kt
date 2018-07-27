package com.example.aravind_pt1748.moviesample105.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.aravind_pt1748.moviesample105.uimodel.MovieCredits
import com.example.aravind_pt1748.moviesample105.uimodel.MovieDetails
import com.example.aravind_pt1748.moviesample105.uimodel.MoviePreview
import com.example.aravind_pt1748.moviesample105.uimodel.Person
import com.example.aravind_pt1748.moviesample105.util.JSONToPersonList
import com.example.aravind_pt1748.moviesample105.util.PersonListToJSON

class DBHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "Movie_Databases"
        const val DATABASE_VERSION = 5

        const val TABLE_PREVIEW_NAME = "Preview_Table"
        const val COLUMN_MOVIE_ID = "MovieId"
        const val COLUMN_MOVIE_TITLE = "MovieTitle"
        const val COLUMN_RELEASE_DATE = "ReleaseDate"
        const val COLUMN_POSTER_PATH = "PosterPath"

        const val TABLE_DETAILS_NAME = "Details_Table"
        const val COLUMN_LANGUAGE = "Language"
        const val COLUMN_OVERVIEW = "Overview"
        const val COLUMN_VOTE_AVERAGE = "VoteAverage"
        const val COLUMN_VOTE_COUNT = "VoteCount"
        const val COLUMN_POPULARITY = "Popularity"
        const val COLUMN_BACKDROP_PATH = "BackdropPath"
        const val COLUMN_GENRES = "Genres"
        const val COLUMN_RUNTIME = "Runtime"

        const val TABLE_CREDITS_NAME = "Credits_Table"
        const val COLUMN_CAST_LIST = "Cast_List"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createPreviewTable = "CREATE TABLE $TABLE_PREVIEW_NAME ( $COLUMN_MOVIE_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_MOVIE_TITLE TEXT, " +
                "$COLUMN_RELEASE_DATE TEXT, " +
                "$COLUMN_POSTER_PATH TEXT)"

        val createDetailsTable = "CREATE TABLE $TABLE_DETAILS_NAME ( $COLUMN_MOVIE_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_MOVIE_TITLE TEXT, " +
                "$COLUMN_RELEASE_DATE TEXT, " +
                "$COLUMN_POSTER_PATH TEXT, " +
                "$COLUMN_LANGUAGE TEXT, " +
                "$COLUMN_OVERVIEW TEXT, " +
                "$COLUMN_VOTE_AVERAGE REAL, " +
                "$COLUMN_POPULARITY REAL, " +
                "$COLUMN_VOTE_COUNT INTEGER, " +
                "$COLUMN_BACKDROP_PATH TEXT, " +
                "$COLUMN_GENRES TEXT, " +
                "$COLUMN_RUNTIME INTEGER)"

        val createCreditsTable = "CREATE TABLE $TABLE_CREDITS_NAME ( $COLUMN_MOVIE_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_CAST_LIST TEXT)"

        db?.execSQL(createPreviewTable)
        db?.execSQL(createDetailsTable)
        db?.execSQL(createCreditsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PREVIEW_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_DETAILS_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CREDITS_NAME")
        onCreate(db)
    }

    fun addMoviePreviews(data : MutableList<MoviePreview>) {
        val db : SQLiteDatabase = this.writableDatabase
        val value : ContentValues = ContentValues()
        var i = 0
        while(i<data.size){
            val preview : MoviePreview = data[i++]
            value.put(COLUMN_MOVIE_ID,preview.movieId)
            value.put(COLUMN_MOVIE_TITLE,preview.movieTitle)
            value.put(COLUMN_RELEASE_DATE,preview.releaseDate)
            value.put(COLUMN_POSTER_PATH,preview.posterPath)
            db.insert(TABLE_PREVIEW_NAME,null,value)
        }
        db.close()
    }

    fun getAllMoviePreviews() : MutableList<MoviePreview> {
        val db : SQLiteDatabase = this.readableDatabase
        val previewList : MutableList<MoviePreview> = mutableListOf()
        val selectQuery = "SELECT * FROM $TABLE_PREVIEW_NAME"
        val cursor = db.rawQuery(selectQuery,null)
        if(cursor.moveToFirst()) {
            do{
                val moviePreview = MoviePreview(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RELEASE_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER_PATH)) )
                previewList.add(moviePreview)
            }while (cursor.moveToNext())
            cursor.close()
        }
        db.close()
        return previewList
    }

    fun updateMoviePreview(data : MoviePreview) {
        val db : SQLiteDatabase = this.writableDatabase
        val values : ContentValues = ContentValues()
        values.put(COLUMN_MOVIE_ID,data.movieId)
        values.put(COLUMN_MOVIE_TITLE,data.movieTitle)
        values.put(COLUMN_RELEASE_DATE,data.releaseDate)
        values.put(COLUMN_POSTER_PATH,data.posterPath)
        db.update( TABLE_PREVIEW_NAME,values,"$COLUMN_MOVIE_ID = ?", Array(1) {data.movieId.toString()} )
        db.close()
    }

    fun deleteMoviePreview(id : Int) {
        val db : SQLiteDatabase = this.writableDatabase
        db.delete(TABLE_PREVIEW_NAME,"$COLUMN_MOVIE_ID = ?", Array(1){id.toString()})
    }

    fun addMovieDetails(details : MovieDetails) {
        val db : SQLiteDatabase = this.writableDatabase
        val value : ContentValues = ContentValues()
        value.put(COLUMN_MOVIE_ID,details.movieId)
        value.put(COLUMN_MOVIE_TITLE,details.movieTitle)
        value.put(COLUMN_RELEASE_DATE,details.releaseDate)
        value.put(COLUMN_POSTER_PATH,details.posterPath)
        value.put(COLUMN_LANGUAGE,details.language)
        value.put(COLUMN_OVERVIEW,details.overview)
        value.put(COLUMN_VOTE_AVERAGE,details.voteAverage)
        value.put(COLUMN_VOTE_COUNT,details.voteCount)
        value.put(COLUMN_POPULARITY,details.popularity)
        value.put(COLUMN_BACKDROP_PATH,details.backdropPath)
        value.put(COLUMN_GENRES,details.genres)
        value.put(COLUMN_RUNTIME,details.runTime)
        db.insert(TABLE_DETAILS_NAME,null,value)
        db.close()
    }

    fun getMovieDetails(id:Int) : MovieDetails {
        val db : SQLiteDatabase = this.readableDatabase
        lateinit var movieDetails : MovieDetails
        val cursor = db.query(TABLE_DETAILS_NAME,null,"$COLUMN_MOVIE_ID = ?", Array(1){id.toString()},null,null,null)
        if(cursor.moveToFirst()) {
                movieDetails = MovieDetails(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RELEASE_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER_PATH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LANGUAGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_VOTE_AVERAGE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_POPULARITY)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VOTE_COUNT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BACKDROP_PATH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRES)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RUNTIME)) )
            cursor.close()
        }
        else{
            movieDetails = MovieDetails()
        }
        db.close()
        return movieDetails
    }

    fun addMovieCredits(credits: MovieCredits) {
        val db = this.writableDatabase
        val value : ContentValues = ContentValues()
        val castList = PersonListToJSON(credits.crew)
        value.put(COLUMN_MOVIE_ID,credits.movieId)
        value.put(COLUMN_CAST_LIST,castList)
        db.insert(TABLE_CREDITS_NAME,null,value)
        db.close()
    }

    fun getMovieCredits(id : Int) : MovieCredits {
        val db = this.readableDatabase
        lateinit var movieCredits : MovieCredits
        val cursor = db.query(TABLE_CREDITS_NAME,null,"$COLUMN_MOVIE_ID = ?", Array(1){id.toString()},null,null,null)
        if(cursor.moveToFirst()) {
            movieCredits = MovieCredits(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_ID)),
                    JSONToPersonList( cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAST_LIST)) )
            )
            cursor.close()
        }
        else{
            movieCredits = MovieCredits()
        }
        db.close()
        return movieCredits
    }
}
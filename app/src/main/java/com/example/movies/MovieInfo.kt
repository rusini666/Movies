package com.example.movies

import androidx.room.Entity

@Entity(tableName = "movie-info-table")
data class MovieInfo(
    val Title: String,
    val Year: Int,
    val Rated: String,
    //val Released:"14 Oct 1994",
    //val Runtime:"142 min",
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String
)

package com.example.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_info_table")
data class MovieInfo(
    @PrimaryKey(autoGenerate = true) var uid: Long = 0,
    @ColumnInfo(name = "Title") var title: String? = null,
    @ColumnInfo(name = "Year") var year: String? = null,
    @ColumnInfo(name = "Rated") var rated:String? = null,
    @ColumnInfo(name = "Released") var released:String? = null,
    @ColumnInfo(name = "Runtime") var runtime:String? = null,
    @ColumnInfo(name = "Genre") var genre:String? = null,
    @ColumnInfo(name = "Director") var director:String? = null,
    @ColumnInfo(name = "Writer") var writer:String? = null,
    @ColumnInfo(name = "Actors") var actors:String? = null,
    @ColumnInfo(name = "Plot") var plot:String? = null
)

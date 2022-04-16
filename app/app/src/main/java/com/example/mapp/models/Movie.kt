package com.example.mapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
public class Movie{
    @PrimaryKey(autoGenerate = true) var uid: Long = 0
    @ColumnInfo(name = "title") var title: String? = null
    @ColumnInfo(name = "year") var year: String? = null
    @ColumnInfo(name = "rated") var rated:String? = null
    @ColumnInfo(name = "released") var released:String? = null
    @ColumnInfo(name = "runtime") var runtime:String? = null
    @ColumnInfo(name = "genre") var genre:String? = null
    @ColumnInfo(name = "director") var director:String? = null
    @ColumnInfo(name = "writer") var writer:String? = null
    @ColumnInfo(name = "actors") var actors:String? = null
    @ColumnInfo(name = "plot") var plot:String? = null
}
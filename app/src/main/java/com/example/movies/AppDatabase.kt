package com.example.movies

import androidx.room.Database


@Database(entities = [MovieInfo::class], version = 1)
abstract class AppDatabase {
    abstract fun movieInfoDao(): MovieInfoDao
}
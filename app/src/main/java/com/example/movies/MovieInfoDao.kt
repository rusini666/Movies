package com.example.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieInfoDao {
    @Insert
    suspend fun insertAll(vararg movieInfo: MovieInfo)

    @Query(SELECT * FROM movie-info-table)
    suspend fun getAll(): List<MovieInfo>
}
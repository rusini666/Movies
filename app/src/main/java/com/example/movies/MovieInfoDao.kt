package com.example.movies

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlin.collections.List as List1

@Dao
interface MovieInfoDao {
    @Query("SELECT * FROM movie_info_table")
    fun getAll(): Flow<List1<MovieInfo>>

    @Query("SELECT * FROM movie_info_table WHERE uid IN (:movieIds)")
    fun loadAllByIds(movieIds: IntArray): Flow<List1<MovieInfo>>

    @Query("SELECT * FROM movie_info_table WHERE title LIKE '%' || :title || '%'")
    fun findByTitle(title: String): Flow<List1<MovieInfo>>

    @Query("SELECT * FROM movie_info_table WHERE actors LIKE '%' || :actor || '%'")
    fun findByActor(actor: String): Flow<List1<MovieInfo>>

    @Insert
    suspend fun insertAll(users: List1<MovieInfo>)

    @Insert
    suspend fun insert(user: MovieInfo)

    @Delete
    fun deleteAll(movie: MovieInfo)
}
package com.example.mapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mapp.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlin.collections.List as List1

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAll(): Flow<List1<Movie>>

    @Query("SELECT * FROM movie WHERE uid IN (:movieIds)")
    fun loadAllByIds(movieIds: IntArray): Flow<List1<Movie>>

    @Query("SELECT * FROM movie WHERE title LIKE '%' || :title || '%'")
    fun findByTitle(title: String): Flow<List1<Movie>>

    @Query("SELECT * FROM movie WHERE actors LIKE '%' || :actor || '%'")
    fun findByActor(actor: String): Flow<List1<Movie>>

    @Insert
    suspend fun insertAll(users: List1<Movie>)

    @Insert
    suspend fun insert(user: Movie)

    @Delete
    fun delete(movie: Movie)
}
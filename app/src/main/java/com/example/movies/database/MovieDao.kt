package com.example.movies.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlin.collections.List as List1

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun getAll(): Flow<List1<Movie>>

    @Query("SELECT * FROM movie_table WHERE uid IN (:movieIds)")
    fun loadAllByIds(movieIds: IntArray): Flow<List1<Movie>>

    @Query("SELECT * FROM movie_table WHERE title LIKE '%' || :title || '%'")
    fun findByTitle(title: String): Flow<List1<Movie>>

    @Query("SELECT * FROM movie_table WHERE actors LIKE '%' || :actor || '%'")
    fun findByActor(actor: String): Flow<List1<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List1<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: Movie)

    @Delete
    fun delete(movie: Movie)
}
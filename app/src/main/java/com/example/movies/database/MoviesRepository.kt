package com.example.movies.database

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.movies.services.OMDBService
import kotlinx.coroutines.flow.Flow

class MoviesRepository(private val movieDao: MovieDao, lifecycleScope: LifecycleCoroutineScope, context:Context) {
    private val  lifecycleCoroutineScope = lifecycleScope
    private val  context = context

    fun loadAllByIds(movieIds: IntArray): Flow<List<Movie>> {
        return  movieDao.loadAllByIds(movieIds)
    }

    fun findByTitle(title: String): Flow<List<Movie>> {
        return movieDao.findByTitle(title)
    }

    fun findByActor(actor: String): Flow<List<Movie>> {
        return movieDao.findByActor(actor)
    }

    fun findByTitleInWeb(title: String): List<Movie> {
        return OMDBService(lifecycleCoroutineScope, context).findMoviesByTitle(title)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(movies: List<Movie>) {
        movieDao.insertAll(movies)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(movie: Movie) {
        movieDao.insert(movie)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(movie: Movie) {
        movieDao.delete(movie)
    }
}
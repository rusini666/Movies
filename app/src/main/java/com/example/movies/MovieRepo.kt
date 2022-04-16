package com.example.movies

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.movies.services.OMDBService
import kotlinx.coroutines.flow.Flow

class MoviesRepository(private val movieDao: MovieInfoDao, lifecycleScope: LifecycleCoroutineScope, context:Context) {
    private val  lifecycleCoroutineScope = lifecycleScope
    private val  context = context

    fun loadAllByIds(movieIds: IntArray): Flow<List<MovieInfo>> {
        return  movieDao.loadAllByIds(movieIds)
    }

    fun findByTitle(title: String): Flow<List<MovieInfo>> {
        return movieDao.findByTitle(title)
    }

    fun findByActor(actor: String): Flow<List<MovieInfo>> {
        return movieDao.findByActor(actor)
    }

    fun findByTitleInWeb(title: String): List<MovieInfo> {
        return OMDBService(lifecycleCoroutineScope, context).findMoviesByTitle(title)
    }

    @WorkerThread
    suspend fun insertAll(movies: List<MovieInfo>) {
        movieDao.insertAll(movies)
    }

    @WorkerThread
    suspend fun insert(movie: MovieInfo) {
        movieDao.insert(movie)
    }

    @WorkerThread
    suspend fun delete(movie: MovieInfo) {
        movieDao.deleteAll(movie)
    }
}
package com.example.movies.services

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.movies.database.Movie
import org.json.JSONObject
import java.lang.Exception


class OMDBService(scope: LifecycleCoroutineScope, context: Context) {
        private var url = "https://www.omdbapi.com"
        private var scope: LifecycleCoroutineScope = scope
        private var context: Context = context

    /**
     * Fetch movies from www.omdbapi.com by title
     * @param title movie title
     * @return movies
     */
    fun findMoviesByTitle(title: String) : List<Movie> {
            var movies = ArrayList<Movie>()
            var results = NetworkService.get("${url}/?t=${title}&apikey=1790e8dc")


                try {
                    //initialize json object of the results
                    var jsonMovie = JSONObject(results)

                    //check whether the response has correct correct structure
                    if(!jsonMovie.has("Response")){
                        throw  Exception()
                    }

                    if(jsonMovie.getBoolean("Response")){
                        var movie = Movie()
                        movie.title = jsonMovie.getString("Title")
                        movie.actors = jsonMovie.getString("Actors")
                        movie.director = jsonMovie.getString("Director")
                        movie.genre = jsonMovie.getString("Genre")
                        movie.plot = jsonMovie.getString("Plot")
                        movie.rated = jsonMovie.getString("Rated")
                        movie.released = jsonMovie.getString("Released")
                        movie.runtime = jsonMovie.getString("Runtime")
                        movie.writer = jsonMovie.getString("Writer")
                        movie.year = jsonMovie.getString("Year")
                        movies.add(movie)
                    }

                }catch (e:Exception){
                    throw  Exception("An error occurred when fetching the movies.")
                }


            return movies
        }

}
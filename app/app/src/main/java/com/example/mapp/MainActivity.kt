package com.example.mapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.mapp.databases.AppDatabase
import com.example.mapp.repositories.MoviesRepository
import com.example.mapp.models.Movie
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val db by lazy { AppDatabase.getDatabase(this).movieDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //set click event listeners for all four navigation buttons

        findViewById<Button>(R.id.add_movies_to_db_btn)
                .setOnClickListener{
                    //fetch movies and save to the database
                    storeMoviesInDB(populateMovies())
                }
        findViewById<Button>(R.id.search_for_movies_btn)
                .setOnClickListener{
                    //show search for movies activity
                    showSearchForMoviesActivity()
                }
        findViewById<Button>(R.id.search_for_actors_btn)
                .setOnClickListener{
                    //show search for actors activity
                    showSearchForActorsActivity()
                }

        findViewById<Button>(R.id.search_movies_in_web_btn)
                .setOnClickListener{
                    //show search movies in web activity
                    showSearchMoviesInWebActivity()
                }
    }

    /**
     * Generate list of movies
     * @returns generated movies
     */
    private fun populateMovies() : ArrayList<Movie> {
        var movies = ArrayList<Movie>()

        var movie1 = Movie()
        movie1.title = "The Shawshank Redemption"
        movie1.year = "1994"
        movie1.rated = "R"
        movie1.released = "14 Oct 1994"
        movie1.runtime = "142 min"
        movie1.genre = "Drama"
        movie1.director = "Frank Darabont"
        movie1.writer = "Stephen King, Frank Darabont"
        movie1.actors = "Tim Robbins, Morgan Freeman, Bob Gunton"
        movie1.plot = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency."
        movies.add(movie1)

        var movie2 = Movie()
        movie2.title = "Batman: The Dark Knight Returns, Part 1"
        movie2.year = "2012"
        movie2.rated = "PG-13"
        movie2.released = "25 Sep 2012"
        movie2.runtime = "76 min"
        movie2.genre = "Animation, Action, Crime, Drama, Thriller"
        movie2.director = "Jay Oliva"
        movie2.writer = "Bob Kane (character created by: Batman), Frank Miller (comic book), Klaus Janson (comic book), Bob Goodman"
        movie2.actors = "Peter Weller, Ariel Winter, David Selby, Wade Williams"
        movie2.plot = "Batman has not been seen for ten years. A new breed of criminal ravages Gotham City, forcing 55-year-old Bruce Wayne back into the cape and cowl. But, does he still have what it takes to fight crime in a new era?"
        movies.add(movie2)

        var movie3 = Movie()
        movie3.title = "The Lord of the Rings: The Return of the King"
        movie3.year = "2003"
        movie3.rated = "PG-13"
        movie3.released = "17 Dec 2003"
        movie3.runtime = "201 min"
        movie3.genre = "Action, Adventure, Drama"
        movie3.director = "Peter Jackson"
        movie3.writer = "J.R.R. Tolkien, Fran Walsh, Philippa Boyens"
        movie3.actors = "Elijah Wood, Viggo Mortensen, Ian McKellen"
        movie3.plot = "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring."
        movies.add(movie3)

        var movie4 = Movie()
        movie4.title = "Inception"
        movie4.year = "2010"
        movie4.rated = "PG-13"
        movie4.released = "16 Jul 2010"
        movie4.runtime = "148 min"
        movie4.genre = "Action, Adventure, Sci-Fi"
        movie4.director = "Christopher Nolan"
        movie4.writer = "Christopher Nolan"
        movie4.actors = "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page"
        movie4.plot = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster."
        movies.add(movie4)

        var movie5 = Movie()
        movie5.title = "The Matrix"
        movie5.year = "1999"
        movie5.rated = "R"
        movie5.released = "31 Mar 1999"
        movie5.runtime = "136 min"
        movie5.genre = "Action, Sci-Fi"
        movie5.director = "Lana Wachowski, Lilly Wachowski"
        movie5.writer = "Lilly Wachowski, Lana Wachowski"
        movie5.actors = "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss"
        movie5.plot = "When a beautiful stranger leads computer hacker Neo to a forbidding underworld, he discovers the shocking truth--the life he knows is the elaborate deception of an evil cyber-intelligence."
        movies.add(movie5)

        return movies
    }

    /**
     * Save given movies in the database
     * @param movies
     */
    private fun storeMoviesInDB(movies:ArrayList<Movie>){
        lifecycleScope.launch {
            MoviesRepository(db, lifecycleScope, applicationContext).insertAll(movies)
            Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Show 'search for movies' activity
     */
    private fun showSearchForMoviesActivity(){
        intent = Intent(applicationContext, SearchForMoviesActivity::class.java)
        startActivity(intent);
    }

    /**
     * show 'search for actors' activity
     */
    private fun showSearchForActorsActivity(){
        intent = Intent(applicationContext, SearchForActorActivity::class.java)
        startActivity(intent);
    }

    /**
     * show 'search movies in web' activity
     */
    private fun showSearchMoviesInWebActivity(){
        intent = Intent(applicationContext, SearchMoviesInWebActivity::class.java)
        startActivity(intent);
    }
}
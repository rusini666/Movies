package com.example.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchMovieBtn = findViewById<Button>(R.id.searchMovieBtn)
        val searchActorBtn = findViewById<Button>(R.id.searchActorBtn)
        val addMovieBtn = findViewById<Button>(R.id.addMovieBtn)

        // create an instance of the database
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "movie-info-database").build()
        var movieInfoDao = db.movieInfoDao()

        val searchMovie = Intent(this,SearchMovies::class.java)
        val searchActor = Intent(this,SearchActors::class.java)

        searchMovieBtn.setOnClickListener {
            startActivity(searchMovie)
        }

        searchActorBtn.setOnClickListener {
            startActivity(searchActor)
        }

        addMovieBtn.setOnClickListener {

        }
    }
}
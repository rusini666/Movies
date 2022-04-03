package com.example.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SearchMovies : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movies)

        val saveMovieToDbBtn = findViewById<Button>(R.id.saveMovieToDbBtn)
        val typeMovie = findViewById<TextView>(R.id.typeMovie)
        val retrieveMovie = findViewById<Button>(R.id.retrieveMovieBtn)

        saveMovieToDbBtn.setOnClickListener {

        }

        retrieveMovie.setOnClickListener {

        }
    }
}
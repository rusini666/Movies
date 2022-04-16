package com.example.mapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapp.databases.AppDatabase
import com.example.mapp.models.Movie
import com.example.mapp.repositories.MoviesRepository
import com.example.mapp.ui.helpers.MovieListAdapter
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchForActorActivity : AppCompatActivity() {
    private val db by lazy { AppDatabase.getDatabase(this).movieDao() }
    private lateinit var moviesRepo:MoviesRepository
    var movies = ArrayList<Movie>()
    var moviesViewModel = MoviesViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_for_actor)

        //configer toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        //define the necessary components
        val searchTextEdit = findViewById<EditText>(R.id.search_text_edit)
        val searchBtn = findViewById<AppCompatImageButton>(R.id.search_btn)
        val searchInfoFrame = findViewById<LinearLayout>(R.id.search_info)
        val searchNoInfoFrame =  findViewById<LinearLayout>(R.id.no_search_results)
        val loadingFrame =  findViewById<FrameLayout>(R.id.loading_spinner)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.movies_recycler_view)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // This will pass the ArrayList to our Adapter
        val adapter = MovieListAdapter(movies)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        //observe the changes against local movies variable
        moviesViewModel.movies.observe(this, Observer {
            //update the local movie list
            movies.clear()
            movies.addAll(it)

            //notify other components about the changes
            adapter.notifyDataSetChanged()
        })

        //set click event handler for search  button
        searchBtn.setOnClickListener {
            //prepare the search word
            val searchWord = searchTextEdit.text?.trim().toString()

            //search the movies
            searchMovies(searchWord, adapter, {
                //show the loading animation
                loadingFrame.visibility = View.VISIBLE
            }, {

                //hide the no results and message sections
                searchInfoFrame.visibility = View.GONE
                searchNoInfoFrame.visibility = View.GONE

                if(searchWord.isEmpty()){
                    //show message section
                    searchInfoFrame.visibility = View.VISIBLE
                }else{
                    if(movies.size < 1){
                        //show no results section
                        searchNoInfoFrame.visibility = View.VISIBLE
                    }
                }
                //hide the loading animation
               loadingFrame.visibility = View.GONE
            })
        }
        moviesRepo = MoviesRepository(db, lifecycleScope, applicationContext)
    }

    /**
     * Search movies by actors
     *
     * @param searchTerm actor name
     * @param adapter data adapter
     * @param before callback that should execute before
     */
    private fun searchMovies(searchTerm: String, adapter: MovieListAdapter, before: () -> Unit, after: () -> Unit){
        before()
        movies.clear()
        if(searchTerm.isNotEmpty()){

            //Launch a new coroutine
            lifecycleScope.launch(Dispatchers.IO){
                moviesRepo.findByActor(searchTerm).collect { mvs->
                    //execute in main context
                    withContext(Dispatchers.Main) {
                        //update the view models
                        moviesViewModel.movies.value = mvs as ArrayList<Movie>
                        after()
                    }
                }
            }

        }else{
            //notify the changes
            adapter.notifyDataSetChanged()
            after()
        }
    }

    /**
     * Main view model
     */
    class MoviesViewModel : ViewModel() {
        val movies = MutableLiveData<List<Movie>>()
    }
}
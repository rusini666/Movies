package com.example.movies

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapp.R
import com.example.movies.adapter.MovieListAdapter
import com.example.movies.database.AppDatabase
import com.example.movies.database.Movie
import com.example.movies.database.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SearchForMoviesActivity : AppCompatActivity() {
    private val db by lazy { AppDatabase.getDatabase(this).movieDao() }
    private lateinit var moviesRepo: MoviesRepository
    var movies = ArrayList<Movie>()
    var vModel = VViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_for_movies)

        //configure the toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        //define the necessary components
        val searchTextEdit = findViewById<EditText>(R.id.search_text_edit)
        val searchBtn = findViewById<Button>(R.id.search_btn)
        val saveBtn = findViewById<Button>(R.id.save_btn)
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
        vModel = ViewModelProvider(this).get(VViewModel::class.java)

        //observe the changes against local movies variable
        vModel.movies.observe(this, Observer {
            //update the local movie list
            movies.clear()
            movies.addAll(it)

            //notify other components about the changes
            adapter.notifyDataSetChanged()
        })

        vModel.showLoading.observe(this) {
            if (it) {
                loadingFrame.visibility = View.VISIBLE
            } else {
                loadingFrame.visibility = View.GONE
            }
        }
        vModel.showInfoFrame.observe(this) {
            if (it) {
                searchInfoFrame.visibility = View.VISIBLE
            } else {
                searchInfoFrame.visibility = View.GONE
            }
        }
        vModel.showNoInfoFrame.observe(this) {
            if (it) {
                searchNoInfoFrame.visibility = View.VISIBLE
            } else {
                searchNoInfoFrame.visibility = View.GONE
            }
        }

        //set click event handler for search  button
        searchBtn.setOnClickListener {
            //prepare the search word
            val searchWord = searchTextEdit.text?.trim().toString()

            //search the movies
            searchMovies(searchWord, adapter, {
                //show the loading animation
                vModel.showLoading.value = true
            }, {
                //hide the no results and message sections
                var sInfoFrame = false
                var sNoInfoFrame = false


                if (searchWord.isEmpty()) {
                    //show message section
                    sInfoFrame = true
                } else {
                    if (movies.size < 1) {
                        //show no results section
                        sNoInfoFrame = true
                    }
                }
                //hide the loading animation
                vModel.showLoading.value = false
                vModel.showInfoFrame.value = sInfoFrame
                vModel.showNoInfoFrame.value = sNoInfoFrame
            })
        }

        saveBtn.setOnClickListener {
            storeMoviesInDB(movies)
        }
        moviesRepo = MoviesRepository(db, lifecycleScope, this)
    }

    /**
     * Save given movies in the database
     * @param movies
     */
    private fun storeMoviesInDB(movies: java.util.ArrayList<Movie>){
        lifecycleScope.launch {
            MoviesRepository(db, lifecycleScope, applicationContext).insertAll(movies)
            Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
        }
    }


    /**
     * Search movies by title
     *
     * @param searchTerm movie title
     * @param adapter data adapter
     * @param before callback that should execute before
     */
    private fun searchMovies(searchTerm: String, adapter: MovieListAdapter, before: () -> Unit, after: () -> Unit){
        before()
        movies.clear()
        if(searchTerm.isNotEmpty()){

            //Launch a new coroutine
            lifecycleScope.launch(Dispatchers.IO){
                try {
                    var newMovies = moviesRepo.findByTitleInWeb(searchTerm)

                    //execute in main context
                    withContext(Dispatchers.Main) {
                        //update the view models
                        vModel.movies.value = newMovies
                        adapter.notifyDataSetChanged()
                        after()
                    }
                }catch (e:Exception){
                    //execute in main context
                    withContext(Dispatchers.Main){
                        //show error message
                        Toast.makeText(applicationContext, e.localizedMessage,Toast.LENGTH_SHORT)
                                .show()
                        after()
                    }
                }
            }

        }else{
            adapter.notifyDataSetChanged()
            after()
        }
    }

    /**
     * Main view model
     */
    class VViewModel : ViewModel() {
        val movies = MutableLiveData<List<Movie>>()
        var showLoading = MutableLiveData<Boolean>()
        var showInfoFrame = MutableLiveData<Boolean>()
        var showNoInfoFrame = MutableLiveData<Boolean>()
    }
}
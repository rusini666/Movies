package com.example.mapp.ui.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mapp.R
import com.example.mapp.models.Movie

class MovieListAdapter(private val movies : List<Movie>) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the movie_view_model_item view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_view_model_item, parent, false)

        return ViewHolder(view)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return movies.size
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]

        //load the attribute holders with data
        holder.titleTxt.text = movie.title.toString()
        holder.yearTxt.text = movie.year.toString()
        holder.director.text = "Director: " + movie.director.toString()
        holder.releasedTxt.text = "Released: " + movie.released.toString()
        holder.ratedTxt.text = "Rated: "+ movie.rated.toString()
        holder.runtimeTxt.text = "Runtime: " + movie.runtime.toString()
        holder.writerTxt.text = "Genre: " + movie.writer.toString()
        holder.genreTxt.text = "Genre: " + movie.genre.toString()
        holder.actorsTxt.text = "Actors: " + movie.actors.toString()
        holder.plotTxt.text = "Plot: " + movie.plot.toString()

    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //define the attribute holders
        var titleTxt: TextView = ItemView.findViewById(R.id.movie_title_txt_view)
        var yearTxt:TextView = ItemView.findViewById(R.id.movie_year_txt_view)
        var director:TextView = ItemView.findViewById(R.id.movie_director_txt_view)
        var releasedTxt:TextView = ItemView.findViewById(R.id.movie_released_txt_view)
        var ratedTxt:TextView = ItemView.findViewById(R.id.movie_rated_txt_view)
        var runtimeTxt:TextView = ItemView.findViewById(R.id.movie_runtime_txt_view)
        var writerTxt:TextView = ItemView.findViewById(R.id.movie_writer_txt_view)
        var genreTxt:TextView = ItemView.findViewById(R.id.movie_genre_txt_view)
        var actorsTxt:TextView = ItemView.findViewById(R.id.movie_actors_txt_view)
        var plotTxt:TextView = ItemView.findViewById(R.id.movie_plot_txt_view)

    }
}
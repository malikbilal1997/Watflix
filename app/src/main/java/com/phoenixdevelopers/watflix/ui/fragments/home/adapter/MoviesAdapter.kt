package com.phoenixdevelopers.watflix.ui.fragments.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phoenixdevelopers.watflix.databinding.ItemMovieGridBinding
import com.phoenixdevelopers.watflix.databinding.ItemMovieVerticalBinding
import com.phoenixdevelopers.watflix.model.Movie
import com.phoenixdevelopers.watflix.utils.MovieDiff
import com.phoenixdevelopers.watflix.utils.getImageUrl

class MoviesAdapter(
    private val isGrid: Boolean,
    private val onItemClickLister: (Movie) -> Unit
) :
    ListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (isGrid) {

            val binding: ItemMovieGridBinding = ItemMovieGridBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            MovieGridViewHolder(binding)

        } else {

            val binding: ItemMovieVerticalBinding = ItemMovieVerticalBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            MovieListViewHolder(binding)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val movie: Movie = getItem(position)

        if (isGrid) {

            (holder as MovieGridViewHolder).bind(movie)

        } else {

            (holder as MovieListViewHolder).bind(movie)
        }

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    inner class MovieGridViewHolder(

        private val binding: ItemMovieGridBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {

                onItemClickLister(getItem(adapterPosition))
            }
        }

        fun bind(movie: Movie) {

            binding.apply {

                itemName.text = movie.title
                itemRating.text = movie.rating

                Glide.with(root).load(getImageUrl(movie.coverImage)).into(itemImage)

            }
        }
    }

    inner class MovieListViewHolder(

        private val binding: ItemMovieVerticalBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {

                onItemClickLister(getItem(adapterPosition))
            }
        }

        fun bind(movie: Movie) {

            binding.apply {

                itemYear.text = movie.year
                itemName.text = movie.title
                itemGenre.text = movie.genre
                itemRating.text = movie.rating
                itemDetails.text = movie.summary

                Glide.with(root).load(getImageUrl(movie.coverImage)).into(itemImage)

            }
        }
    }


}
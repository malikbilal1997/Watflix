package com.phoenixdevelopers.watflix.ui.fragments.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.phoenixdevelopers.watflix.databinding.ItemMovieHorizontalBinding
import com.phoenixdevelopers.watflix.model.Movie
import com.phoenixdevelopers.watflix.utils.MovieDiff
import com.phoenixdevelopers.watflix.utils.getImageUrl

class SimilarAdapter(

    private val onItemClickLister: (Movie) -> Unit

) : ListAdapter<Movie, ViewHolder>(MovieDiff) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val movie: Movie = getItem(position)

        (holder as MovieViewHolder).bind(movie)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return MovieViewHolder(
            ItemMovieHorizontalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class MovieViewHolder(

        private val binding: ItemMovieHorizontalBinding

    ) : ViewHolder(binding.root) {

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

}
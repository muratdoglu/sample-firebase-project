package com.pickup.rsbyoqg0ny.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.pickup.rsbyoqg0ny.data.model.Movie
import com.pickup.rsbyoqg0ny.databinding.ItemPopularBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_popular.view.*

class PopularsAdapter(
    private var movies: ArrayList<Movie>,
    private val listener: OnItemClickListener
) : androidx.recyclerview.widget.RecyclerView.Adapter<PopularsAdapter.TripItemViewHolder>() {

    override fun onBindViewHolder(holder: TripItemViewHolder, position: Int) {
        return holder.bind(movies[position], listener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripItemViewHolder =
        TripItemViewHolder.create(parent)


    override fun getItemCount() = movies.count()


    class TripItemViewHolder(private var binding: ItemPopularBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: Movie,
            listener: OnItemClickListener
        ) = with(binding) {

            Picasso.with(itemView.context)
                .load("http://image.tmdb.org/t/p/w500" + movie.poster_path).into(binding.ivPoster)

            itemView.setOnClickListener {
                movie?.let { it1 ->
                    listener.onItemClick(it1)
                }
            }
            binding.movie = movie

        }

        companion object {
            fun create(parent: ViewGroup?): TripItemViewHolder {
                val binding =
                    ItemPopularBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
                return TripItemViewHolder(binding)
            }
        }
    }


    interface OnItemClickListener {
        fun onItemClick(item: Movie)
    }

}
package com.mhasancse15.cleanmaster.presentation.movieList.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import com.mhasancse15.cleanmaster.common.constants.AppConstant
import com.mhasancse15.cleanmaster.domain.model.Movie
import om.mhasancse15.cleanmaster.R
import om.mhasancse15.cleanmaster.databinding.RowMovieBinding
import timber.log.Timber


class MovieViewHolder(private val binding: RowMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie, onItemClickListener: ((Movie) -> Unit)? = null) {
        binding.apply {

            movieNameTextView.text = movie.title
            if (movie.posterPath?.isNotBlank() == true) {
                imageView.load(AppConstant.POSTER_PATH + movie.posterPath) {
                    placeholder(R.drawable.ic_launcher_background)

                    listener(
                        onSuccess = { _, _ ->
                            Timber.tag("imageIssue").d("Success image Url = ${ movie.posterPath}")
                        },
                        onError = { request: ImageRequest, error: ErrorResult ->
                            request.error
                            imageView.load(R.drawable.ic_launcher_background)
                            Timber.tag("imageIssue")
                                .d("Exception image Url = ${movie.posterPath} Error $error")

                        }
                    )
                }
            } else {
                imageView.load(R.drawable.ic_launcher_background)
            }

            itemView.setOnClickListener {
                onItemClickListener?.invoke(movie)
            }
        }
    }
}

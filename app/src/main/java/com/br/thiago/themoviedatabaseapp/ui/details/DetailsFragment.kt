package com.br.thiago.themoviedatabaseapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.br.thiago.themoviedatabaseapp.R
import com.br.thiago.themoviedatabaseapp.api.MovieService
import com.br.thiago.themoviedatabaseapp.databinding.FragmentDetailsBinding
import com.br.thiago.themoviedatabaseapp.model.Movie
import com.bumptech.glide.Glide

class DetailsFragment : Fragment(), DetailsContract.View {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    private var presenter: DetailsPresenter? = null
    private var isFavoriteMovie: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = DetailsPresenter(this)
        getMovieDetails()
        binding.fabAddFavorite.setOnClickListener {
            val movie = presenter?.movie
            movie?.let {
                presenter?.addOrRemoveFromDatabase(movie, isFavoriteMovie, requireContext())
            }
        }
    }

    private fun getMovieDetails() {
        isFavoriteMovie = presenter?.isFavoriteMovie(args.isFromDatabase, requireContext()) ?: false
        showLoadingScreen()
        presenter?.getMovieDetails(args.movieId, args.isFromDatabase, requireContext())
    }

    override fun setupLayout(movie: Movie) {
        binding.tvOriginalTitle.text = movie.originalTitle
        Glide
            .with(requireContext())
            .load("${MovieService.BASE_IMAGE_URL}${movie.backdropPath}")
            .placeholder(R.drawable.ic_movie_image_placeholder)
            .centerCrop()
            .into(binding.ivBackDrop)
    }

    override fun showLoadingScreen() {
        binding.loadingGroup.visibility = View.VISIBLE
    }

    override fun hideLoadingScreen() {
        binding.loadingGroup.visibility = View.GONE
    }

    override fun setFabAsFavoriteMovie() {
        binding.fabAddFavorite.setImageDrawable(
            context?.let { ContextCompat.getDrawable(it, R.drawable.ic_baseline_favorite_24) }
        )
        isFavoriteMovie = true
    }

    override fun setFabAsNotFavoriteMovie() {
        binding.fabAddFavorite.setImageDrawable(
            context?.let {
                ContextCompat.getDrawable(
                    it,
                    R.drawable.ic_baseline_favorite_border_24
                )
            }
        )
        isFavoriteMovie = true
    }

}
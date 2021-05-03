package com.br.thiago.themoviedatabaseapp.ui.details

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.br.thiago.themoviedatabaseapp.R
import com.br.thiago.themoviedatabaseapp.api.MovieService
import com.br.thiago.themoviedatabaseapp.databinding.FragmentDetailsBinding
import com.br.thiago.themoviedatabaseapp.model.Movie
import com.br.thiago.themoviedatabaseapp.util.Formatter.Companion.getMoneyFormat
import com.bumptech.glide.Glide

class DetailsFragment : Fragment(), DetailsContract.View {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    private var presenter: DetailsPresenter? = null
    private var isFavoriteMovie: Boolean = false
    private lateinit var movie: Movie

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
        presenter?.destroyView()
        presenter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = DetailsPresenter(this, MovieService.create())
        getMovieDetails()
        binding.btnFavoriteIcon.setOnClickListener {
            showLoadingScreen()
            presenter?.addOrRemoveFromParse(
                movie,
                isFavoriteMovie,
                requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            )
        }
    }

    private fun getMovieDetails() {
        showLoadingScreen()
        presenter?.getMovieDetails(
            args.movieId,
            args.isFromFavoritesFragment,
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        )
    }

    override fun setupLayout(movie: Movie) {
        Glide
            .with(requireContext())
            .load("${MovieService.BASE_IMAGE_URL}${movie.backdropPath}")
            .placeholder(R.drawable.ic_movie_image_placeholder)
            .centerCrop()
            .into(binding.ivBackDrop)
        Glide
            .with(requireContext())
            .load("${MovieService.BASE_IMAGE_URL}${movie.posterPath}")
            .placeholder(R.drawable.ic_movie_image_placeholder)
            .centerCrop()
            .into(binding.ivPoster)
        binding.tvTitle.text = movie.title
        binding.tvBudgetValue.text = getMoneyFormat(movie.budget ?: 0)
        binding.tvOverviewDescription.text = movie.overview
        binding.tvReleaseDateValue.text = movie.releaseDate?.replace("-", "/")
        binding.tvStatusValue.text = movie.status
        binding.tvRevenueValue.text = getMoneyFormat(movie.revenue ?: 0)
        binding.tvOriginalTitleValue.text = movie.originalTitle
        binding.tvRuntimeValue.text = movie.runtime.toString() + " min"
        binding.tvVoteAverageValue.text = movie.voteAverage.toString()
    }

    override fun showLoadingScreen() {
        binding.loadingGroup.visibility = View.VISIBLE
    }

    override fun hideLoadingScreen() {
        binding.loadingGroup.visibility = View.GONE
    }

    override fun setFabAsFavoriteMovie() {
        isFavoriteMovie = true
        binding.btnFavoriteIcon.setImageDrawable(
            context?.let { ContextCompat.getDrawable(it, R.drawable.ic_baseline_red_favorite_24) }
        )
    }

    override fun setFabAsNotFavoriteMovie() {
        isFavoriteMovie = false
        binding.btnFavoriteIcon.setImageDrawable(
            context?.let {
                ContextCompat.getDrawable(
                    it,
                    R.drawable.ic_baseline_favorite_border_24
                )
            }
        )
    }

    override fun setMovie(movie: Movie) {
        this.movie = movie
    }

    override fun showNoInternetConnectionWarning() {
        Toast.makeText(
            requireContext(),
            getString(R.string.no_internet_connection),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun showErrorMessage() {
        Toast.makeText(
            requireContext(),
            getString(R.string.unexpected_error_occurred),
            Toast.LENGTH_LONG
        ).show()
    }

}
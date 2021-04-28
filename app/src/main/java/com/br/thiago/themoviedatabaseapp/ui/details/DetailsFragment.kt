package com.br.thiago.themoviedatabaseapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.br.thiago.themoviedatabaseapp.R
import com.br.thiago.themoviedatabaseapp.api.MovieService
import com.br.thiago.themoviedatabaseapp.api.getdetails.GetMovieDetailsResponse
import com.br.thiago.themoviedatabaseapp.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide

class DetailsFragment : Fragment(), DetailsContract.View {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    private var presenter: DetailsPresenter? = null

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
        getDetailsFromApi()
    }

    private fun getDetailsFromApi() {
        presenter?.getDetailsFromApi(args.movieId)
    }

    override fun setupLayout(details: GetMovieDetailsResponse) {
        binding.tvOriginalTitle.text = details.original_title
        Glide
            .with(requireContext())
            .load("${MovieService.BASE_IMAGE_URL}${details.backdrop_path}")
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

}
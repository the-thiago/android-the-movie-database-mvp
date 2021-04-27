package com.br.thiago.themoviedatabaseapp.fragments

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDetailsFromApi()
    }

    private fun getDetailsFromApi() {
        CoroutineScope(Dispatchers.IO).launch {
            val details = MovieService.create().getMovieDetails(args.movieId).body()
            details?.let {
                withContext(Dispatchers.Main) {
                    setupLayout(details)
                }
            }
        }
    }

    private fun setupLayout(details: GetMovieDetailsResponse) {
        binding.tvOriginalTitle.text = details.original_title
        Glide
            .with(requireContext())
            .load("${MovieService.BASE_IMAGE_URL}${details.backdrop_path}")
            .placeholder(R.drawable.ic_movie_image_placeholder)
            .centerCrop()
            .into(binding.ivBackDrop)
    }

}
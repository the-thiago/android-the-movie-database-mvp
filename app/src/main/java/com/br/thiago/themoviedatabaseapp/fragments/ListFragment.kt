package com.br.thiago.themoviedatabaseapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.br.thiago.themoviedatabaseapp.adapter.MovieAdapter
import com.br.thiago.themoviedatabaseapp.api.MovieService
import com.br.thiago.themoviedatabaseapp.databinding.FragmentListBinding
import com.br.thiago.themoviedatabaseapp.model.Movie
import com.br.thiago.themoviedatabaseapp.util.getMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { MovieAdapter(::clickItem) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter

        var movies = emptyList<Movie>()
        CoroutineScope(Dispatchers.IO).launch {
            val moviesRequest = MovieService.create().getMovies()
            if (moviesRequest.isSuccessful) {
                moviesRequest.body()?.getMovies()?.let {
                    movies = it
                }
                withContext(Dispatchers.Main) {
                    adapter.setItems(movies)
                }
            }
        }
    }

    private fun clickItem(movie: Movie) {
        findNavController().navigate(
            ListFragmentDirections.actionListFragmentToDetailsFragment(
                movieId = movie.id
            )
        )
    }

}
package com.br.thiago.themoviedatabaseapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.br.thiago.themoviedatabaseapp.adapter.MovieAdapter
import com.br.thiago.themoviedatabaseapp.databinding.FragmentListBinding
import com.br.thiago.themoviedatabaseapp.model.Movie

class ListFragment : Fragment(), ListContract.View {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { MovieAdapter(::clickItem) }
    private var presenter: ListPresenter? = null

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
        presenter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ListPresenter(this)
        binding.recyclerView.adapter = adapter
        presenter?.getMoviesFromApi()
    }

    private fun clickItem(movie: Movie) {
        findNavController().navigate(
            ListFragmentDirections.actionListFragmentToDetailsFragment(
                movieId = movie.movieId,
                isFromDatabase = false
            )
        )
    }

    override fun showMovieList(movies: List<Movie>) {
        adapter.setItems(movies)
    }

    override fun showLoadingScreen() {
        binding.loadingGroup.visibility = View.VISIBLE
    }

    override fun hideLoadingScreen() {
        binding.loadingGroup.visibility = View.GONE
    }

}
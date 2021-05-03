package com.br.thiago.themoviedatabaseapp.ui.search

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.br.thiago.themoviedatabaseapp.R
import com.br.thiago.themoviedatabaseapp.adapter.MovieAdapter
import com.br.thiago.themoviedatabaseapp.databinding.FragmentSearchBinding
import com.br.thiago.themoviedatabaseapp.model.Movie

class SearchFragment : Fragment(), SearchContract.View {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { MovieAdapter(::clickItem) }
    private var presenter: SearchPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
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
        presenter = SearchPresenter(this)
        binding.recyclerView.adapter = adapter
        searchMovies()
        binding.tvSearchResultStatus.text = getString(R.string.type_to_search)
    }

    private fun clickItem(movie: Movie) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
                movieId = movie.movieId,
                isFromFavoritesFragment = false
            )
        )
    }

    private fun searchMovies() {
        binding.etSearchField.addTextChangedListener { editable ->
            val query = editable.toString()
            presenter?.searchMovies(
                query,
                requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            )
        }
    }

    override fun showLoadingScreen() {
        binding.loadingGroup.visibility = View.VISIBLE
    }

    override fun hideLoadingScreen() {
        binding.loadingGroup.visibility = View.GONE
    }

    override fun showSearchedMovies(movies: List<Movie>) {
        binding.tvSearchResultStatus.visibility = View.GONE
        adapter.setItems(movies)
    }

    override fun showEmptySearch() {
        adapter.setItems(emptyList())
        binding.tvSearchResultStatus.text = getString(R.string.no_movies_found)
        binding.tvSearchResultStatus.visibility = View.VISIBLE
    }

    override fun showNoInternetConnectionWarning() {
        Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_LONG).show()
    }

}
package com.br.thiago.themoviedatabaseapp.ui.list

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.thiago.themoviedatabaseapp.adapter.MovieAdapter
import com.br.thiago.themoviedatabaseapp.databinding.FragmentNowPlayingBinding
import com.br.thiago.themoviedatabaseapp.model.Movie
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.QUERY_PAGE_SIZE

class NowPlayingFragment : Fragment(), NowPlayingContract.View {

    private var _binding: FragmentNowPlayingBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { MovieAdapter(::clickItem) }
    private var presenter: NowPlayingPresenter? = null

    private var isError = false
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate =
                isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                        isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                presenter?.getMoviesFromApi(
                    requireContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                )
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNowPlayingBinding.inflate(inflater, container, false)
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
        presenter = NowPlayingPresenter(this)
        binding.recyclerView.apply {
            adapter = this@NowPlayingFragment.adapter
            addOnScrollListener(this@NowPlayingFragment.scrollListener)
        }
        presenter?.getMoviesFromApi(
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        )
    }

    private fun clickItem(movie: Movie) {
        findNavController().navigate(
            NowPlayingFragmentDirections.actionListFragmentToDetailsFragment(
                movieId = movie.movieId,
                isFromFavoritesFragment = false
            )
        )
    }

    override fun showMovieList(movies: List<Movie>) {
        adapter.setItems(movies)
        hideLoadingScreen()
    }

    override fun showLoadingScreen() {
        binding.loadingGroup.visibility = View.VISIBLE
        isLoading = true
    }

    override fun hideLoadingScreen() {
        binding.loadingGroup.visibility = View.GONE
        isLoading = false
    }

    override fun showNoInternetConnectionWarning() {
        Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_LONG).show()
    }

}
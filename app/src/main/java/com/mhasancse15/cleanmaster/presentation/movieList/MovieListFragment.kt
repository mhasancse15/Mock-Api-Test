package com.mhasancse15.cleanmaster.presentation.movieList

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels

import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.mhasancse15.cleanmaster.presentation.movieList.adapter.MovieAdapter
import com.mhasancse15.cleanmaster.common.baseComponent.BaseFragment
import com.mhasancse15.cleanmaster.domain.util.autoCleared
import com.mhasancse15.cleanmaster.presentation.movieList.adapter.MovieLoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import om.mhasancse15.cleanmaster.BuildConfig
import om.mhasancse15.cleanmaster.R
import om.mhasancse15.cleanmaster.databinding.FragmentMovieListBinding

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding>() {
   // private val viewModel by viewModels<MovieListViewModel>()

    private val viewModel by viewModels<MovieListViewModel>()


    private val moviePagingDataAdapter = MovieAdapter()

    override fun viewBindingLayout(): FragmentMovieListBinding = FragmentMovieListBinding.inflate(layoutInflater)

    override fun initializeView(savedInstanceState: Bundle?) {
        initAdapter()
        observeUI()
        setListener()
        initViewCollect()
    }

    private fun observeUI() {
        binding.movieRecyclerView.adapter = moviePagingDataAdapter.withLoadStateFooter(
            footer = MovieLoadingStateAdapter { moviePagingDataAdapter.retry() }
        )

        lifecycleScope.launch {
            moviePagingDataAdapter.loadStateFlow.collect { loadState ->
                binding.apply {
                    movieRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                    progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                    handleError(loadState)
                }
            }
        }
    }

    private fun handleError(loadState: CombinedLoadStates) {
        binding.apply {
            if (loadState.source.refresh is LoadState.Error) {
                errorText.isVisible = loadState.source.refresh is LoadState.Error
                errorText.text =
                    (loadState.source.refresh as LoadState.Error).error.localizedMessage
            } else if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached  && moviePagingDataAdapter.itemCount < 1) {
                errorText.isVisible = true
                errorText.text = getString(R.string.not_found, viewModel.wrapperType)
                emptyAnimation.isVisible = true
            } else {
                errorText.isVisible = false
                emptyAnimation.isVisible = false
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initAdapter() {
        viewModel.movieList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                moviePagingDataAdapter.notifyDataSetChanged()
                moviePagingDataAdapter.submitData(it)
            }
        }
    }

    private fun setListener() {
        moviePagingDataAdapter.onItemClickListener = {
//            findNavController().navigate(
//                R.id.action_movieFragment_to_movieDetailsFragment,
//                bundleOf("movie_id" to movieItem.id.toString())
//            )
        }
    }

    private fun initViewCollect() {
        with(viewModel) {
            with(binding) {
                movieRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
                movieRecyclerView.apply {
                    layoutManager
                    adapter = moviePagingDataAdapter
                }
                movieRecyclerView.layoutManager
                getMovieList(BuildConfig.API_KEY, "en-US")
            }
        }
    }


}
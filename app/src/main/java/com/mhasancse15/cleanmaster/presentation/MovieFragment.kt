package com.mhasancse15.cleanmaster.presentation

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.mhasancse15.cleanmaster.common.baseComponent.BaseFragment
import com.mhasancse15.cleanmaster.presentation.movieList.adapter.MovieAdapter
import com.mhasancse15.cleanmaster.presentation.movieList.adapter.MovieLoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import om.mhasancse15.cleanmaster.BuildConfig
import om.mhasancse15.cleanmaster.R
import om.mhasancse15.cleanmaster.databinding.FragmentMovieBinding
import om.mhasancse15.cleanmaster.databinding.FragmentMovieListBinding


@AndroidEntryPoint
class MovieFragment :Fragment() {
    private val viewModel: MovieViewModel by viewModels()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val moviePagingDataAdapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewCollect()
        observeUI()
        setListener()

        initAdapter()
        /*  moviePagingDataAdapter.setOnItemClickListener {
              findNavController().navigate(
                  R.id.action_movieFragment_to_movieDetailsFragment,
                  bundleOf("movie_id" to it.id.toString())
              )
          }*/
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

    private fun setListener() {
        moviePagingDataAdapter.onItemClickListener = { searchItem ->
           /* findNavController().navigate(
                R.id.action_movieFragment_to_movieDetailsFragment,
                bundleOf("movie_id" to searchItem.id.toString())
            )*/
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
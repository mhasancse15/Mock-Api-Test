package com.mhasancse15.task.presentation.book

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mhasancse15.task.common.baseComponent.BaseFragment
import com.mhasancse15.task.common.utils.MockResponseInterceptor
import com.mhasancse15.task.common.utils.Resource
import com.mhasancse15.task.databinding.FragmentBookListBinding
import com.mhasancse15.task.presentation.book.adapter.BookAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

import timber.log.Timber

@AndroidEntryPoint
class BookListFragment : BaseFragment<FragmentBookListBinding>() {
    private val viewModel: BookListViewModel by viewModels()
    private var booksAdapter: BookAdapter? = null
    override fun viewBindingLayout(): FragmentBookListBinding = FragmentBookListBinding.inflate(layoutInflater)

    override fun initializeView(savedInstanceState: Bundle?) {
        initListener()
        setUpRecyclerView()
        viewModel.getBookList()
        initViewCollect()

    }

    private fun initListener() {
        isGlobalMockingEnabled = !isGlobalMockingEnabled

        context?.assets?.let { MockResponseInterceptor.Builder(it).build() }
    }

    companion object {
        var isGlobalMockingEnabled = true
    }

    private fun setUpRecyclerView() {
        booksAdapter = BookAdapter()
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = booksAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

    }

    private fun initViewCollect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect{response ->
                when(response){
                    is Resource.Loading -> {
                        Timber.tag("Response").e("Loading")
                    }
                    is Resource.Success -> {
                        Timber.tag("Response").e("Response Size = ${response.data.data?.size}")
                        booksAdapter?.differ?.submitList(response.data.data)
                    }
                    is Resource.Error -> {
                        Timber.tag("Response").e(response.throwable.localizedMessage ?: "Error")
                    }

                    else -> {
                        Timber.tag("Response").d("Unknown Error")
                    }
                }
            }
        }
    }

}
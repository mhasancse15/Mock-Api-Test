package com.mhasancse15.cleanmaster.common.baseComponent

import androidx.recyclerview.widget.DiffUtil
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

 abstract class BaseListAdapter<T,V : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
 ) : ListAdapter<T, ListAdapterViewHolder<V>>(diffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapterViewHolder<V> {
        val binding = createBinding(parent)
        return ListAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListAdapterViewHolder<V>, position: Int) {

        bind(holder.binding, getItem(position), position)
    }

    protected abstract fun createBinding(parent: ViewGroup): V
    protected abstract fun bind(binding: V, item: T, position: Int)
}

class ListAdapterViewHolder<out T : ViewBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)


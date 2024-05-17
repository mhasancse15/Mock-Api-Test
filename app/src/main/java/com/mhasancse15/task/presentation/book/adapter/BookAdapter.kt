package com.mhasancse15.task.presentation.book.adapter

import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import com.mhasancse15.task.R
import com.mhasancse15.task.data.model.Book
import com.mhasancse15.task.databinding.ItemBookBinding
import timber.log.Timber


class BookAdapter: RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var binding: ItemBookBinding? = null

    inner class BookViewHolder(itemBinding: ItemBookBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }

     val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookViewHolder(requireNotNull(binding))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = differ.currentList[position]
        holder.itemView.apply {
            binding?.apply {
               bookName.text = book.name

                if (book.image?.isNotBlank() == true) {
                    bookImage.load(book.image) {
                        placeholder(R.drawable.ic_launcher_background)

                        listener(
                            onSuccess = { _, _ ->
                                Timber.tag("imageResponse").d(  "Success image Url = ${book.image}")
                            },
                            onError = { request: ImageRequest, error: ErrorResult ->
                                request.error
                                Timber.tag("imageResponse").d( "Exception image Url = ${book.image}  Error $error")
                                bookImage.load(R.drawable.ic_launcher_background)
                            }
                        )
                    }
                } else {
                    bookImage.load(R.drawable.ic_launcher_background)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}
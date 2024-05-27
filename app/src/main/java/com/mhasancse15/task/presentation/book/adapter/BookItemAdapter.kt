package com.mhasancse15.task.presentation.book.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import com.mhasancse15.task.R
import com.mhasancse15.task.data.model.Book
import com.mhasancse15.task.databinding.ItemBookBinding
import timber.log.Timber

class BookItemAdapter : ListAdapter<Book, BookItemAdapter.ExampleItemViewHolder>(ExampleItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleItemViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExampleItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExampleItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ExampleItemViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.bookName.text = book.name

            if (book.image?.isNotBlank() == true) {
                binding.bookImage.load(book.image) {
                    placeholder(R.drawable.ic_launcher_background)

                    listener(
                        onSuccess = { _, _ ->
                            Timber.tag("imageResponse").d(  "Success image Url = ${book.image}")
                        },
                        onError = { request: ImageRequest, error: ErrorResult ->
                            request.error
                            Timber.tag("imageResponse").d( "Exception image Url = ${book.image}  Error $error")
                            binding.bookImage.load(R.drawable.ic_launcher_background)
                        }
                    )
                }
            } else {
                binding.bookImage.load(R.drawable.ic_launcher_background)
            }
        }
    }

    class ExampleItemDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}

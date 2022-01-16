package com.example.imagesearchplus.UI.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.menu.MenuView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.imagesearchplus.Data.UnsplashPhoto
import com.example.imagesearchplus.R
import com.example.imagesearchplus.databinding.ItemGalleryBinding

class UnsplashPagingAdapter(private val listener: onItemClickListner) :
    PagingDataAdapter<UnsplashPhoto, UnsplashPagingAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {


    companion object {
        
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: UnsplashPhoto,
                newItem: UnsplashPhoto
            ): Boolean = oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)

    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }


    inner class PhotoViewHolder(private val binding: ItemGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: UnsplashPhoto) {
            binding.apply {
                Glide.with(itemView)
                    //Loading photo into recyclerview
                    .load(photo.urls.small)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(itemGallery)
                //username of the author of the photo
                textviewUsername.text = photo.user.username
                // number of likes the photo got so far
                textviewlikes.text = photo.likes.toString()
            }
        }

        //Enable click on Recyclerview items
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }

            }
        }
    }

    //Our Fragment will later implement this interface and then we can pass fragment itself over
    // the constrcutor to the adapter and call this method on it. We could also pass the fragment
    // without this interface but that way we will tightly couple the adapter  to the fragement
    // but with the help of this interface we can reuse this interface in another fragment later
    // if we want to. 
    interface onItemClickListner {
        fun onItemClick(photo: UnsplashPhoto)
    }
}
package com.example.imagesearchplus.UI.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.example.imagesearchplus.Data.UnsplashPhoto
import com.example.imagesearchplus.R
import com.example.imagesearchplus.databinding.FragmentGalleryBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.unsplash_photo_load_state_footer.*
import java.util.zip.Inflater
@AndroidEntryPoint
class FragmentGallery : Fragment(R.layout.fragment_gallery),
    UnsplashPagingAdapter.onItemClickListner {


    private val viewModel by viewModels<GalleryViewModel>()
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val adapter = UnsplashPagingAdapter(this)

        //Loading adapter incase of any error
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter { adapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter { adapter.retry() },
            )
            retryButton.setOnClickListener {
                adapter.retry()
            }
        }
        viewModel.photo.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        adapter.addLoadStateListener { loadstate ->
            binding.apply {
                progressBar.isVisible = loadstate.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadstate.source.refresh is LoadState.NotLoading
                retryButton.isVisible = loadstate.source.refresh is LoadState.Error
                textviewEror.isVisible = loadstate.source.refresh is LoadState.Error

                //Empty view
                if (loadstate.source.refresh is LoadState.NotLoading &&
                    loadstate.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }

            }
        }
        //otherwise the search image will not appear in OptionsMenu
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.search_photo)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchPhoto(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onItemClick(photo: UnsplashPhoto) {
        TODO("Not yet implemented")
    }
}
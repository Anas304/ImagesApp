package com.example.imagesearchplus.UI.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.imagesearchplus.Data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val repository: UnsplashRepository

) : ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "kotlin"
    }

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val photo = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhoto(query : String){
        currentQuery.value = query
    }


}
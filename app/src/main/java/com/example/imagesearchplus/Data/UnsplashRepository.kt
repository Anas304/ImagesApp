package com.example.imagesearchplus.Data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.imagesearchplus.API.UnsplashAPI
import javax.inject.Inject
import javax.inject.Singleton

/**Repository modules handle data operations. They provide a clean API so that the rest of the app
 * can retrieve this data easily. They know where to get the data from and what API calls to make when data is updated.
 * You can consider repositories to be mediators between different data sources, such as persistent models, web services, and caches.
Our UnsplashRepository class, shown in the following code snippet, uses an instance of UnsplashAPI to fetch  photos data:*/
@Singleton
class UnsplashRepository @Inject constructor(private val unsplashAPI: UnsplashAPI) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {UnsplashPagingSource(unsplashAPI, query)}
        ).liveData


}
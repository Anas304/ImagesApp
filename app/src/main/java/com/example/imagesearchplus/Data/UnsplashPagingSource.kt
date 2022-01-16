package com.example.imagesearchplus.Data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imagesearchplus.API.UnsplashAPI
import com.example.imagesearchplus.API.UnsplashResponse
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1
class UnsplashPagingSource(
    private val unsplashAPI: UnsplashAPI,
    private val query : String
): PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashAPI.searchPhotos(query,position,params.loadSize)
            val photos = response.results
            LoadResult.Page(
                data = photos,
                //This means that the user is at first page
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position -1,
                nextKey = if (photos.isEmpty()) null else position +1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
        catch (e : HttpException){
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        TODO("Not yet implemented")
    }


}
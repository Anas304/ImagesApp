package com.example.imagesearchplus.DependencyInjection

import com.example.imagesearchplus.API.UnsplashAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Why object? Cuz we want only one instance of this class in our whole app

@Module
@InstallIn(SingletonComponent :: class)
object DependencyInjection {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UnsplashAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUnsplashAPI(retrofit: Retrofit): UnsplashAPI =
        retrofit.create(UnsplashAPI::class.java)

}
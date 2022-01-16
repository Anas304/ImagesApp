package com.example.imagesearchplus.Data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/** The kotlin-parcelize plugin provides a Parcelable implementation generator.
 * Parcelable:: Interface for classes whose instances can be written to and restored from a Parcel
 * Parcel: A Container for a message (Object or Reference)*/

// Simply putting :
//In simple terms Parcelable is used to send a whole object of a model class to another page.
// In your code this is in the model and it is storing int value size to Parcelable object to send and retrieve in other activity
@Parcelize
data class UnsplashPhoto(
    val id : String,
    val description : String,
    val likes : Int,
    val urls : UnsplashUrls,
    val user : UnsplashUser
) : Parcelable {

    @Parcelize
    data class UnsplashUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val thumb: String,
        val small: String
    ) : Parcelable

    @Parcelize
    data class UnsplashUser(
        val username : String,
        val name : String,
    ) : Parcelable{
        // Unsplash API require us to give proper Attribution when we use one of their images and we
        // do this via this URL
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}



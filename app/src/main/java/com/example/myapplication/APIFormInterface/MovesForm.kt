package com.example.myapplication.APIFormInterface

import com.example.myapplication.APIResponce.FilmsDataResponce

import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Part


interface MovesForm {
    @GET("3/discover/movie?include_adult=false&include_video=false&language=en-US&sort_by=popularity.desc")
    suspend fun GetMovies(
        @retrofit2.http.Query("page") page:Int,
        @retrofit2.http.Query("with_genres") Category:String): Response<FilmsDataResponce>
}
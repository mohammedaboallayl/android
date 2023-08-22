package com.example.myapplication.Report

import com.example.myapplication.APIFormInterface.MovesForm
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
class GetFilmsReport  {

    val MovesForm = RetrofitHelper.getInstance().create(MovesForm::class.java)

    suspend fun GetFilms(page:Int,Category:String) = MovesForm.GetMovies(page,Category)
}
@Singleton
object RetrofitHelper {

//    val URL="http://10.0.2.2:4000"
    val URL="https://api.themoviedb.org"
    private val AuthorizationT="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0MzM1MmJhYTkxYWNhMzUyMjU4ZjE4NTM1ZTYzODZjOCIsInN1YiI6IjY0ZGI4MDBiMzcxMDk3MDBhYzQyZmUwMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.WIItulU24fJA1pw-9PlFjt5lBloJzQDGX-cj5y6Xzww"
    private val httpclient=OkHttpClient.Builder()
    fun getInstance(): Retrofit {
        httpclient.addInterceptor { chain ->
            val original: Request = chain.request()
            val request: Request =
                original.newBuilder().header("Authorization", "Bearer " + AuthorizationT)
                    .method(original.method(), original.body())
                    .build()
            chain.proceed(request)
        }
        return Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpclient.build())
            .build()
    }
}

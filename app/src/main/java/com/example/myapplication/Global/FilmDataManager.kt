package com.example.myapplication.Global

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.example.myapplication.APIResponce.ResponceData.FilmDataRes


class FilmDataManager {
    private var filmdata: FilmDataRes?=null
    private var filmImage: Bitmap?=null

    fun setFilmData(data:FilmDataRes){
        filmdata=data
    }fun setFilmImage(data:Bitmap){
        filmImage=data
    }
    fun getFilmData():FilmDataRes? {
        return filmdata
    }
    fun getFilmImage(): Bitmap? {
        return filmImage
    }
    companion object{
        @Volatile
        private  var instance:FilmDataManager?=null
        fun getInstance():FilmDataManager{
            return instance?: synchronized(this){
                instance?:FilmDataManager().also { instance=it }
            }

        }
    }

}
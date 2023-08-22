package com.example.myapplication.FilmsDataContainer


import com.example.myapplication.APIResponce.FilmsDataResponce
import com.example.myapplication.APIResponce.ResponceData.FilmDataRes
import com.example.myapplication.RecAdapter.PopularFilmsAdapter

class FilmsDataContainer{
    private var filmsdata: FilmsDataResponce?=null
    private var PosterImagesNames=ArrayList<String>()
    var page=1
    var isDataIn =0
    fun setIsDataIn(type:Int){
        isDataIn=type
    }
    fun setFilmsData(data: FilmsDataResponce?,adapter: PopularFilmsAdapter?){
        filmsdata=data
        var i=0
        while (i< data!!.results.size){
            PosterImagesNames.add(data!!.results[i].poster_path)
            adapter!!.notifyItemInserted(PosterImagesNames.size-1)
           i++
        }
    }
    fun addFilmsData(data: FilmsDataResponce?,adapter: PopularFilmsAdapter?){
        filmsdata?.results?.addAll(data!!.results)
        var i=0
        while (i< data!!.results.size){
            PosterImagesNames.add(data.results[i].poster_path)
            adapter?.notifyItemInserted(PosterImagesNames.size-1)
           i++
        }
    }
    fun getFilmDataByIndex(index:Int):FilmDataRes{
       return filmsdata!!.results[index]
    }
    fun getPoserImages():ArrayList<String> {
        return PosterImagesNames
    }
    fun getBackgroundImageByIndex(index:Int):String {
        return filmsdata!!.results[index].backdrop_path
    }

    companion object{
        @Volatile
        private  var instance: FilmsDataContainer?=null
        fun getInstance(): FilmsDataContainer {
            return instance?: synchronized(this){
                instance?: FilmsDataContainer().also { instance=it }
            }

        }
    }

}
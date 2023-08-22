package com.example.myapplication.FilmsDataContainer

import android.os.Parcelable
import com.example.myapplication.APIResponce.FilmsDataResponce
import com.example.myapplication.APIResponce.ResponceData.FilmDataRes
import com.example.myapplication.RecAdapter.CategoricalAdapter
import com.example.myapplication.RecAdapter.CategoricalData
import com.example.myapplication.RecAdapter.PopularFilmsAdapter

class CategoricalFilmsDataContainer {
    private var filmsdata= arrayListOf<FilmsDataResponce?>(null,null,null,null,null,null,null)
    var PosterImagesNames=ArrayList<ArrayList<String>>()
    var page= arrayListOf(1,1,1,1,1,1,1)
    var isDataIn =arrayListOf(0,0,0,0,0,0,0)
    val Categories= arrayListOf("Horror","Animation","Family","Drama","Action","Science Fiction","History")
    val CategoriesId= arrayListOf("27","16","10751","18","28","878","10752")
    val CategoriesRecPosition= arrayListOf<Parcelable?>(null,null,null,null,null,null,null)

    fun getCategoriesRecPosition(Category: Int):Parcelable?{
        if (Category>=7){
            return null
        }
            return CategoriesRecPosition[Category]
    }
    fun setCategoriesRecPosition(Category:Int,State: Parcelable?){
        if (Category<7){
            CategoriesRecPosition.set(Category,State)
        }
    }
    fun setIsDataIn(type:Int,Index:Int){
        isDataIn.set(Index,type)
    }
    fun getIsDataIn(Index:Int):Int{
        return isDataIn[Index]
    }
    fun getpage(Category:String):Int{
        return page[CategoriesId.indexOf(Category)]
    }
    fun setPagePlus(Category:String){
        page.set(CategoriesId.indexOf(Category),page[CategoriesId.indexOf(Category)]+1)
    }

    fun setFilmsData(data: FilmsDataResponce?, adapter: PopularFilmsAdapter?,Index: Int){
        filmsdata.set(Index,data)
        var i=0
        while (i< data!!.results.size){
            PosterImagesNames[Index].add(data!!.results[i].poster_path)
            adapter!!.notifyItemInserted(filmsdata[Index]!!.results.size-1)
            i++
        }
    }
    fun addFilmsData(data: FilmsDataResponce?, adapter: PopularFilmsAdapter?,Index: Int){
        filmsdata[Index]!!.results?.addAll(data!!.results)
        var i=0
        while (i< data!!.results.size){
            PosterImagesNames[Index].add(data.results[i].poster_path)
            adapter?.notifyItemInserted(filmsdata[Index]!!.results.size-1)
            i++
        }
    }
    fun getPoster(Category: Int):ArrayList<String>{
        for (i in isDataIn){
            PosterImagesNames.add(ArrayList())
        }
        return PosterImagesNames[Category]
    }

    fun getFilmDataByIndex(Category:Int,index:Int): FilmDataRes {
        return filmsdata[Category]!!.results[index]
    }

    fun getBackgroundImageByIndex(Category: Int,index:Int):String {
        return filmsdata[Category]!!.results[index].backdrop_path
    }

    companion object{
        @Volatile
        private  var instance: CategoricalFilmsDataContainer?=null
        fun getInstance(): CategoricalFilmsDataContainer {
            return instance?: synchronized(this){
                instance?: CategoricalFilmsDataContainer().also { instance=it }
            }

        }
    }

}
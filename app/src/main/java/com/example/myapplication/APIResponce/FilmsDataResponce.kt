package com.example.myapplication.APIResponce

import com.example.myapplication.APIResponce.ResponceData.FilmDataRes



data class FilmsDataResponce (
    val page: Int,
    val results:ArrayList<FilmDataRes> ,
    val total_pages: Int,
    val total_results: Int
)
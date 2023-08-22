package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.myapplication.FilmsDataContainer.CategoricalFilmsDataContainer
import com.example.myapplication.FilmsDataContainer.FilmsDataContainer
import com.example.myapplication.ModelViews.GetMovesViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         val FilmsDatacontainer=FilmsDataContainer.getInstance()
        val cte=CategoricalFilmsDataContainer.getInstance()

    }
}
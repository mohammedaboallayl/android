package com.example.myapplication.ModelViews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.APIResponce.FilmsDataResponce
import com.example.myapplication.Global.getError
import com.example.myapplication.Report.GetFilmsReport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


sealed class DataState{
    object Loading:DataState()
    data class Success(val data:FilmsDataResponce?,val Category: String):DataState()
    data class Error(val error:Throwable):DataState()
}
@HiltViewModel
class GetMovesViewModel @Inject constructor(): ViewModel() {

    private val _gettingFilmsDataState = MutableStateFlow<DataState>(DataState.Loading)
    val gettingFilmsDataState: StateFlow<DataState> = _gettingFilmsDataState

    fun GetFilms(page:Int,Category:String="Any") {
        viewModelScope.launch(Dispatchers.IO) {
                _gettingFilmsDataState.value=DataState.Loading
            try {
                val res = GetFilmsReport().GetFilms(page,Category)
                    _gettingFilmsDataState.value=DataState.Success(res.body(),Category)
            } catch (e: Exception) {
                e.printStackTrace()
                    _gettingFilmsDataState.value=DataState.Error(e)

            }
        }

    }


}
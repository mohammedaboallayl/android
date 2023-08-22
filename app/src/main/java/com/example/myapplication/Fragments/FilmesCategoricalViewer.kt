package com.example.myapplication.Fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.FilmsDataContainer.CategoricalFilmsDataContainer
import com.example.myapplication.FilmsDataContainer.FilmsDataContainer
import com.example.myapplication.Global.FilmDataManager
import com.example.myapplication.ModelViews.DataState
import com.example.myapplication.ModelViews.GetMovesViewModel
import com.example.myapplication.R
import com.example.myapplication.RecAdapter.CategoricalAdapter
import com.example.myapplication.RecAdapter.CategoricalData
import com.example.myapplication.RecAdapter.PopularFilmsAdapter
import com.example.myapplication.databinding.FragmentFilmesCategoricalViewerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilmesCategoricalViewer : Fragment() {
  private lateinit var  binding:FragmentFilmesCategoricalViewerBinding
    private val FilmData= FilmDataManager.getInstance()
    private val viewModel: GetMovesViewModel by activityViewModels()
    private val categoricalData=CategoricalFilmsDataContainer.getInstance()

    var MainAdapter:CategoricalAdapter?=null
    val CategoricaldataAdapter=ArrayList<CategoricalData>()
    private lateinit var connctinState: ConnectivityManager
    private var networkCollback=object: ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            if(categoricalData.isDataIn.indexOf(2)==-1 && FilmsDataContainer.getInstance().isDataIn!=2){
                viewModel.GetFilms(categoricalData.getpage("27"),categoricalData.CategoriesId[0])
            }

            var index=0
            for(i in categoricalData.isDataIn){
                if(i==0){
                    viewModel.GetFilms(categoricalData.getpage(categoricalData.CategoriesId[index]),categoricalData.CategoriesId[index])
                    break
                }
                index++

            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
                Toast.makeText(requireContext(),"Network Lost",Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFilmesCategoricalViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connctinState=requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
            connctinState.registerDefaultNetworkCallback(networkCollback)
        }
        Observe()
        if(CategoricaldataAdapter.size==0){
            Init()
        }

        binding.CatRecycle.layoutManager=LinearLayoutManager(requireContext())
       MainAdapter=CategoricalAdapter(CategoricaldataAdapter,requireContext(),categoricalData)
        binding.CatRecycle.adapter=MainAdapter

    }

    fun Observe(){
    lifecycleScope.launch {
        viewModel.gettingFilmsDataState.collect{state->

                when (state){
                    is DataState.Loading->{

                    }
                    is DataState.Success -> {

                        if (state.Category!="Any"){
                            if(categoricalData.getIsDataIn(categoricalData.CategoriesId.indexOf(state.Category))==1){
                                categoricalData.addFilmsData(state.data, CategoricaldataAdapter[categoricalData.CategoriesId.indexOf(state.Category)].Adapters, categoricalData.CategoriesId.indexOf(state.Category))
                            }else if (categoricalData.getPoster(categoricalData.CategoriesId.indexOf(state.Category)).size==0){
                                categoricalData.setFilmsData(state.data, CategoricaldataAdapter[categoricalData.CategoriesId.indexOf(state.Category)].Adapters, categoricalData.CategoriesId.indexOf(state.Category))
                            }
                            categoricalData.setIsDataIn(1, categoricalData.CategoriesId.indexOf(state.Category))
                        }

                           var index=0
                           for(i in categoricalData.isDataIn){
                               if(i==0){
                                   viewModel.GetFilms(categoricalData.getpage(categoricalData.CategoriesId[index]),categoricalData.CategoriesId[index])
                                   break
                               }
                               index++
                           }

                    }
                    is DataState.Error -> {

                        Toast.makeText(
                                requireContext(),
                                state.error.toString(),
                                Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }
        }

    }
    fun Init(){

        for (i in categoricalData.Categories){
            var adapter=PopularFilmsAdapter(categoricalData.getPoster(categoricalData.Categories.indexOf(i)), requireContext(),viewModel, null,lifecycleScope,categoricalData.CategoriesId[categoricalData.Categories.indexOf(i)])
            CategoricaldataAdapter.add(CategoricalData(adapter,i))

            adapter.setOnClickLisener { position,cat->
                FilmData.setFilmData(categoricalData.getFilmDataByIndex(categoricalData.CategoriesId.indexOf(cat),position))
                categoricalData.setIsDataIn(2,categoricalData.CategoriesId.indexOf(cat))
                FilmsDataContainer.getInstance().setIsDataIn(2)
                findNavController().navigate(R.id.action_filmsListFragment_to_privateFilmeViewer)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
     connctinState.unregisterNetworkCallback(networkCollback)
    }


}
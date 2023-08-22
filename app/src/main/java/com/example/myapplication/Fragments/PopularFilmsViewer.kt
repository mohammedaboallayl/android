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
import androidx.core.content.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.FilmsDataContainer.CategoricalFilmsDataContainer
import com.example.myapplication.FilmsDataContainer.FilmsDataContainer
import com.example.myapplication.Global.FilmDataManager
import com.example.myapplication.ModelViews.DataState
import com.example.myapplication.ModelViews.GetMovesViewModel
import com.example.myapplication.R
import com.example.myapplication.RecAdapter.PopularFilmsAdapter
import com.example.myapplication.databinding.FragmentPopularFilmsViewerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PopularFilmsViewer : Fragment() {
    private val viewModel: GetMovesViewModel by viewModels()
    private val FilmsDatacontainer=FilmsDataContainer.getInstance()
    private val FilmData=FilmDataManager.getInstance()
    private var adapter:PopularFilmsAdapter?=null
   private lateinit var binding: FragmentPopularFilmsViewerBinding
   private lateinit var connctinState:ConnectivityManager
   private val networkCollback= object : ConnectivityManager.NetworkCallback() {
       override fun onAvailable(network: Network) {
           super.onAvailable(network)
           if (FilmsDatacontainer.isDataIn===0){
               binding.Progressbar.visibility=View.VISIBLE
               viewModel.GetFilms(FilmsDatacontainer.page)
           }
       }

       override fun onLost(network: Network) {
           super.onLost(network)
           if (FilmsDatacontainer.isDataIn===0){
               binding.Progressbar.visibility=View.VISIBLE
           }
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
        binding= FragmentPopularFilmsViewerBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connctinState=requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            connctinState.registerDefaultNetworkCallback(networkCollback)
        }
        if (FilmsDatacontainer.isDataIn===0){
            binding.Progressbar.visibility=View.VISIBLE
        }
        observeStates()

        binding.RecyclerView.layoutManager= GridLayoutManager(context,2)
        adapter= PopularFilmsAdapter(FilmsDatacontainer.getPoserImages(),context=this.requireContext(),viewModel,binding.Loading,lifecycleScope)
        binding.RecyclerView.adapter=adapter



        adapter!!.setOnClickLisener { position,cat->
            FilmData.setFilmData(FilmsDatacontainer.getFilmDataByIndex(position))
            FilmsDatacontainer.setIsDataIn(2)
            var intance=CategoricalFilmsDataContainer.getInstance()
            var k=0
            for (i in intance.isDataIn){
                intance.isDataIn[k]=3
                k++
            }
            findNavController().navigate(R.id.action_filmsListFragment_to_privateFilmeViewer)
        }

    }




    private fun observeStates() {
        lifecycleScope.launch {
            viewModel.gettingFilmsDataState.collect{state->

                when (state){
                    is DataState.Loading->{

                    }
                    is DataState.Success->{

                        if(state.Category=="Any"){
                            if(FilmsDatacontainer.isDataIn===1){
                                FilmsDatacontainer.addFilmsData(state.data,adapter)
                            }else if (FilmsDatacontainer.isDataIn==0){
                                FilmsDatacontainer.setFilmsData(state.data,adapter)
                            }
                            FilmsDatacontainer.setIsDataIn(1)
                            binding.Progressbar.visibility=View.GONE
                            binding.Loading.visibility=View.GONE
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

    override fun onDestroy() {
        super.onDestroy()
        connctinState.unregisterNetworkCallback(networkCollback)
    }
}

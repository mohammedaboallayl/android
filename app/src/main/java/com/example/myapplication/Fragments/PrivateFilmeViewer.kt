package com.example.myapplication.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.Global.FilmDataManager
import com.example.myapplication.databinding.FragmentPrivateFilmeViewerBinding
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@AndroidEntryPoint
class PrivateFilmeViewer : Fragment() {
    private val filmdata=FilmDataManager.getInstance().getFilmData()
    private lateinit var binding: FragmentPrivateFilmeViewerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding= FragmentPrivateFilmeViewerBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO){
                var Image1= Glide.with(requireContext()).load("https://image.tmdb.org/t/p/original/${filmdata!!.backdrop_path}")
                var Image2= Glide.with(requireContext()).load("https://image.tmdb.org/t/p/original/${filmdata!!.poster_path}")
                withContext(Dispatchers.Main){
                    Image1.into(binding.itemimg)
                    Image2.into(binding.Poster)
                }
        }
        binding.Rate.setText(filmdata!!.vote_average.toString()+"\\10")
        binding.Description.setText(filmdata!!.overview)
        binding.Lang.setText(filmdata!!.original_language)
        binding.Year.setText((filmdata!!.release_date))
        binding.Title.setText(filmdata!!.title)
        binding.Back.setOnClickListener {
            findNavController().popBackStack()

        }

    }

}
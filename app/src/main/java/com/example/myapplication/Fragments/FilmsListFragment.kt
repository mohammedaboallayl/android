package com.example.myapplication.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.RecAdapter.ViewPagerAdabter
import com.example.myapplication.databinding.FragmentFilmsListBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FilmsListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentFilmsListBinding
    private lateinit var FragmentsArray:Array<Fragment>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
    binding=FragmentFilmsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.toolbar.title="Popular Movies"
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        FragmentsArray= arrayOf(
                PopularFilmsViewer(),
        FilmesCategoricalViewer())

        var viewPagerAdabter=ViewPagerAdabter(this,FragmentsArray)
        binding.ViewPager.adapter=viewPagerAdabter


        TabLayoutMediator(binding.tablayout,binding.ViewPager) {tab,position ->
            if(position==0){
                tab.text="All"
            }else{
                tab.text="Categories"
            }

        }.attach()



    }
}
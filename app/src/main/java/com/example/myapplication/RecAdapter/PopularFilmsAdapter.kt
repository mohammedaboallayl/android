package com.example.myapplication.RecAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.FilmsDataContainer.CategoricalFilmsDataContainer
import com.example.myapplication.FilmsDataContainer.FilmsDataContainer
import com.example.myapplication.ModelViews.GetMovesViewModel
import com.example.myapplication.R
import kotlinx.coroutines.*

class PopularFilmsAdapter(private val data:ArrayList<String>, private val context:Context,private val viewmodel:GetMovesViewModel, private val Loading:LinearLayout?=null,private val LifeCycleScope:CoroutineScope,var category:String="Any") : RecyclerView.Adapter<PopularFilmsAdapter.Holderview>() {
    private var onItemClick:((Int,category:String)->Unit)?=null

    override fun onBindViewHolder(holder: Holderview, position: Int) {
       var item: String =data[position]
        LifeCycleScope.launch(Dispatchers.IO){
            var Image=Glide.with(context).load("https://image.tmdb.org/t/p/original/$item")
            withContext(Dispatchers.Main){
                Image.into(holder.Image)
            }
        }


        if (position==data.size-4 && category=="Any"){
            var container=FilmsDataContainer.getInstance()
            container.page=container.page+1
             viewmodel.GetFilms(container.page,category)
        }else if (position==data.size-4 && category!="Any"){
            var container=CategoricalFilmsDataContainer.getInstance()
            container.setPagePlus(category)
            viewmodel.GetFilms(container.getpage(category),category)
        }
        if (position==data.size-2 && Loading != null){
            Loading.visibility=View.VISIBLE
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position,category)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holderview {
        return Holderview(LayoutInflater.from(parent.context).inflate(R.layout.film_view,parent,false))
    }
    fun setOnClickLisener(litener:(Int,cat:String)->Unit){
        onItemClick=litener
    }
    override fun getItemCount(): Int {
        return data.size
    }
     fun getItem(position:Int): String {
        return data[position]
    }
      class Holderview(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val Image:ImageView=itemView.findViewById(R.id.FielmImg)



    }


}
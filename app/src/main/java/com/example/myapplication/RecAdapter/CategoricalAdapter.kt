package com.example.myapplication.RecAdapter


import android.content.Context
import android.text.method.Touch
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.FilmsDataContainer.CategoricalFilmsDataContainer
import com.example.myapplication.R

class CategoricalAdapter(private val data:ArrayList<CategoricalData>, private val context:Context,private val CategoricalFilmsScroll: CategoricalFilmsDataContainer) : RecyclerView.Adapter<CategoricalAdapter.Holderview>() {
    override fun onBindViewHolder(holder: Holderview, position: Int) {
        var item: CategoricalData =data[position]
        holder.textview.setText(item.Categorical)
        val layoutmanager=GridLayoutManager(context,1,GridLayoutManager.HORIZONTAL,false)
        holder.Recycle.layoutManager=layoutmanager
//        holder.textview.setOnClickListener {
//            val params=ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//            val layout=holder.itemView.findViewById<LinearLayout>(R.id.layout)
//            if (layout.layoutParams!=params){
//                layout.layoutParams=params
//                layoutmanager.orientation=GridLayoutManager.VERTICAL
//            }else{
//                holder.itemView.layoutParams.height=275
//
//            }
//
//        }
        holder.Recycle.adapter=item.Adapters
        if(CategoricalFilmsScroll.getCategoriesRecPosition(position) !=null){
            layoutmanager.onRestoreInstanceState(CategoricalFilmsScroll.getCategoriesRecPosition(position))
        }
     holder.Recycle.setOnScrollChangeListener { view, i, i2, i3, i4 ->
            CategoricalFilmsScroll.setCategoriesRecPosition(position,layoutmanager.onSaveInstanceState() )
        }

        TouchListener(holder.Recycle,item.Categorical,position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holderview {
        return Holderview(LayoutInflater.from(parent.context).inflate(R.layout.categoricalview,parent,false))
    }

    override fun getItemCount(): Int {
        return data.size
    }
    fun getItem(position:Int): CategoricalData {
        return data[position]
    }
    class Holderview(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Recycle:RecyclerView=itemView.findViewById(R.id.RecycleView)
        val textview:TextView=itemView.findViewById(R.id.TextVie)

    }
    fun TouchListener(recyclerview: RecyclerView,Categorical:String,position:Int){
        val listener = object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val action = e.action
                if (recyclerview.canScrollHorizontally(RecyclerView.FOCUS_FORWARD) || recyclerview.canScrollHorizontally(RecyclerView.FOCUS_BACKWARD)) {
                    when (action) {
                        MotionEvent.ACTION_MOVE -> rv.parent
                                .requestDisallowInterceptTouchEvent(true)
                    }
                    return false
                }
                else {
                    when (action) {
                        MotionEvent.ACTION_MOVE -> rv.parent
                                .requestDisallowInterceptTouchEvent(false)
                    }
                    recyclerview.removeOnItemTouchListener(this)
                    return true
                }
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        }
        recyclerview.addOnItemTouchListener(listener)
    }

}
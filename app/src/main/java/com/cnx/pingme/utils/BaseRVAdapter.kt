package com.cnx.pingme.utils

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class BaseRvAdapter<M,H : RecyclerView.ViewHolder> (private val context: Context,
                                                         private val itemViewType: (index: Int) -> Int,
                                                         private val holderMaker :(view : View) -> H,
                                                         mutableList: MutableList<M>,
                                                         private var binder : (H,M,Int) -> Unit) : RecyclerView.Adapter<H>() {

    var items: MutableList<M> = mutableList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {

        val itemView = LayoutInflater.from(context).inflate(viewType,parent,false)
        return holderMaker(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: H, position: Int) {

        val item : M = items[position]
        binder(holder,item,position)
    }


    override fun getItemViewType(position: Int): Int {
        return itemViewType(position)
    }


    fun addItem(item : M) {
        items.add(item)
        notifyDataSetChanged()
    }

    fun updateItems( results: MutableList<M>){

        Log.d("BaseRvAdapter", "items: $items ")

        items = results
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int): M {
        return items[position]
    }

    fun clear() {

        items.clear()
        notifyDataSetChanged()

    }

}
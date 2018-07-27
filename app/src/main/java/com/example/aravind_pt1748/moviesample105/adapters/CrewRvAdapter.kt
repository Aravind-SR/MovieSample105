package com.example.aravind_pt1748.moviesample105.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.aravind_pt1748.moviesample105.BR
import com.example.aravind_pt1748.moviesample105.databinding.CastCardLayoutBinding
import com.example.aravind_pt1748.moviesample105.uimodel.Person

class CrewRvAdapter(private var items: MutableList<Person>)
    : RecyclerView.Adapter<CrewRvAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CastCardLayoutBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        Log.d("RvAdapter","No. of items : ${items.size}")
        return items.size
    }

    override fun onBindViewHolder(holder : MyViewHolder, position : Int) {
        holder.bind(items[position])
    }

    fun replaceData(crewList: MutableList<Person>) {
        items = crewList
        notifyDataSetChanged()
    }


    class MyViewHolder(private var binding: CastCardLayoutBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(castPerson : Person) {
            binding.setVariable(BR.cast,castPerson)
            binding.executePendingBindings()
        }
    }

}
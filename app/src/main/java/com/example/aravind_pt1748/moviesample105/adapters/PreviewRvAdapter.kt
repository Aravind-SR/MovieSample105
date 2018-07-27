package com.example.aravind_pt1748.moviesample105.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.aravind_pt1748.moviesample105.BR
import com.example.aravind_pt1748.moviesample105.databinding.MovieListItemCardLayoutBinding
import com.example.aravind_pt1748.moviesample105.interfaces.OnBottomReachedListener
import com.example.aravind_pt1748.moviesample105.interfaces.OnItemClickListener
import com.example.aravind_pt1748.moviesample105.uimodel.MoviePreview


class PreviewRvAdapter(private var items: MutableList<MoviePreview>,
                       private var listener: OnItemClickListener,
                       private var onBottomReachedListener: OnBottomReachedListener)
    : RecyclerView.Adapter<PreviewRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = MovieListItemCardLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(items[position], listener)
        if(position == (items.size-1)){
            onBottomReachedListener.onBottomReached(position)
        }
    }

    override fun getItemCount(): Int {
        Log.d("RvAdapter","No. of items : ${items.size}")
        return items.size
    }

    fun replaceData(movieList: MutableList<MoviePreview>) {
        items = movieList
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: MovieListItemCardLayoutBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(uiMovieHolder : MoviePreview, listener: OnItemClickListener?) {
            binding.setVariable(BR.moviePreview,uiMovieHolder)
            if (listener != null) {
                binding.root.setOnClickListener { _ -> listener.onItemClick(layoutPosition) }
            }
            binding.executePendingBindings()
        }
    }

}
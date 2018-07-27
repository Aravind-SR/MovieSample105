package com.example.aravind_pt1748.moviesample105.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.aravind_pt1748.moviesample105.R

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) =
        Glide.with(imageView.context)
                .load(url)
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .into(imageView)

package com.example.aravind_pt1748.moviesample105.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.example.aravind_pt1748.moviesample105.R
import com.example.aravind_pt1748.moviesample105.adapters.PreviewRvAdapter
import com.example.aravind_pt1748.moviesample105.databinding.LayoutPreviewBinding
import com.example.aravind_pt1748.moviesample105.uimodel.MoviePreview
import com.example.aravind_pt1748.moviesample105.viewmodel.PreviewViewModel

class PreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: LayoutPreviewBinding = DataBindingUtil.setContentView(this, R.layout.layout_preview)
        val viewModel by lazy { ViewModelProviders.of(this).get(PreviewViewModel::class.java) }
        viewModel.loadRepositories()
        binding.viewModel = viewModel
        binding.executePendingBindings()

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.myRecyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        } else {
            binding.myRecyclerView.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)

        }

        val repositoryRecyclerViewAdapter: PreviewRvAdapter = PreviewRvAdapter(mutableListOf<MoviePreview>(), viewModel.onItemClickInstance, viewModel.onBottomReachedInstance)
        binding.myRecyclerView.adapter = repositoryRecyclerViewAdapter

        viewModel.repositories.observe(this,
                Observer<MutableList<MoviePreview>> { it?.let { repositoryRecyclerViewAdapter.replaceData(it) } })

    }
}

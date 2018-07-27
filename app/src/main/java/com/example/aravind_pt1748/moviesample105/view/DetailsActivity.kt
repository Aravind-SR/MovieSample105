package com.example.aravind_pt1748.moviesample105.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.example.aravind_pt1748.moviesample105.R
import com.example.aravind_pt1748.moviesample105.adapters.CrewRvAdapter
import com.example.aravind_pt1748.moviesample105.databinding.MovieDetailLayoutBinding
import com.example.aravind_pt1748.moviesample105.uimodel.MovieCredits
import com.example.aravind_pt1748.moviesample105.uimodel.MovieDetails
import com.example.aravind_pt1748.moviesample105.uimodel.Person
import com.example.aravind_pt1748.moviesample105.viewmodel.DetailsViewModel

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by lazy { ViewModelProviders.of(this).get(DetailsViewModel::class.java) }
        val binding: MovieDetailLayoutBinding = DataBindingUtil.setContentView(this, R.layout.movie_detail_layout)
        if (savedInstanceState == null) {
            viewModel.loadMovieDetails(intent.getIntExtra("id", 353081))
        }
        viewModel.movieDetails.observe(this,
                Observer<MovieDetails> { binding.viewModel = viewModel;binding.executePendingBindings() })

        viewModel.movieCredits.observe(this,
                Observer<MovieCredits> {
                    it?.let {
                        val crewRvAdapter = CrewRvAdapter(viewModel.movieCredits.value!!.crew)
                        binding.castRecyclerView.adapter = crewRvAdapter
                    }
                })

    }

}
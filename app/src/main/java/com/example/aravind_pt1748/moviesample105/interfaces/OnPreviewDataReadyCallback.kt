package com.example.aravind_pt1748.moviesample105.interfaces

import com.example.aravind_pt1748.moviesample105.uimodel.MoviePreview

interface OnPreviewDataReadyCallback {
    fun onDataReady(data : MutableList<MoviePreview>)
}
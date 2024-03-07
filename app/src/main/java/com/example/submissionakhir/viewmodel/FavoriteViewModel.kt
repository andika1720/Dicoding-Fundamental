package com.example.submissionakhir.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionakhir.database.Favorite
import com.example.submissionakhir.repository.FavoriteRepo


class FavoriteViewModel(application: Application) : ViewModel() {
    private val repoFavo: FavoriteRepo = FavoriteRepo(application)

    fun getAllFavorite(): LiveData<List<Favorite>> = repoFavo.getAllFavorite()
    init {
        getAllFavorite()
    }
}
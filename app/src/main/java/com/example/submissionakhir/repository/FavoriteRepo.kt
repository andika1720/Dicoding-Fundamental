package com.example.submissionakhir.repository

import android.app.Application
import com.example.submissionakhir.database.Favorite
import com.example.submissionakhir.database.FavoriteDao
import com.example.submissionakhir.database.FavoriteDatabase

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepo (application: Application) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val mFavoriteDao: FavoriteDao

    init {
        val database = FavoriteDatabase.getInstance(application)
        mFavoriteDao = database.favoriteDao
    }

    fun insert (favorite: Favorite){
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun isFavorite(username: String) = mFavoriteDao.isFavorite(username)


    fun getAllFavorite() = mFavoriteDao.getAllFavorites()

    fun deleteFavorite(favorite: Favorite) {
        executorService.execute { mFavoriteDao.delete(favorite) }
    }
}
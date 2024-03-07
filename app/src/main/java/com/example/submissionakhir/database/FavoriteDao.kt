package com.example.submissionakhir.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Query("SELECT * FROM user_favorite")
    fun getAllFavorites() : LiveData<List<Favorite>>

    @Query("SELECT EXISTS(SELECT * FROM user_favorite WHERE username = :username)")
    fun isFavorite(username: String): LiveData<Boolean>

    @Delete
    fun delete(favorite: Favorite)
}
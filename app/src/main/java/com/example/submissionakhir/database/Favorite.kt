package com.example.submissionakhir.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity (tableName = "user_favorite")
data class Favorite(
    @PrimaryKey
    @ColumnInfo(name = "username")
    var username: String = "",
    @ColumnInfo(name = "avatar")
    var avatarUrl: String? = null,

): Parcelable

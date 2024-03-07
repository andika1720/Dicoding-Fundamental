package com.example.submissionakhir.network.response

import com.google.gson.annotations.SerializedName

data class DataItems(
    @SerializedName("avatar_url")
    val avatarUrl: String?,

    @SerializedName("login")
    val login: String?,
    @SerializedName("bio")
    val bio: Any?,
)
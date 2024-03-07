package com.example.submissionakhir.network.response

import com.google.gson.annotations.SerializedName

data class ResponseList(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean?,
    @SerializedName("items")
    val items: List<DataItems>,
    @SerializedName("total_count")
    val totalCount: Int?
)

package com.davidgarza.takehome.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repo(
    @SerializedName("id") val idRepo: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("updated_at") val updated_at: String?,
    @SerializedName("stargazers_count") val stargazers_count: Int,
    @SerializedName("forks") val forks: Int
): Parcelable

package com.davidgarza.takehome.data.remote

import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("name") val name: String? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
)

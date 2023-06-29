package com.davidgarza.takehome.api

import com.davidgarza.takehome.data.remote.Repo
import com.davidgarza.takehome.data.remote.UserDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") username: String): Response<List<Repo>>

    @GET("users/{username}")
    suspend fun getUserDetails(@Path("username") username: String): Response<UserDetails>
}
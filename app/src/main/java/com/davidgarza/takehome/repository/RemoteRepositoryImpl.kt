package com.davidgarza.takehome.repository

import com.davidgarza.takehome.util.Constants.ERROR_FETCHING_DATA
import com.davidgarza.takehome.api.ApiInterface
import com.davidgarza.takehome.data.remote.UserDetails
import com.davidgarza.takehome.util.Response
import kotlinx.coroutines.flow.flow

class RemoteRepositoryImpl(
    private val api: ApiInterface
) : RemoteRepository {

    override suspend fun searchUserRepos(username: String) = flow {
        try {
            val response = api.getUserRepos(username)
            if (response.isSuccessful) {
                emit(Response.Success(response.body() ?: emptyList()))
            }
            else emit(Response.Error(ERROR_FETCHING_DATA))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response.Error(ERROR_FETCHING_DATA))
        }
    }

    override suspend fun getUserDetails(username: String) = flow {
        try {
            val response = api.getUserDetails(username)
            if (response.isSuccessful) {
                emit(Response.Success(response.body() ?: UserDetails()))
            }
            else emit(Response.Error(ERROR_FETCHING_DATA))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response.Error(ERROR_FETCHING_DATA))
        }
    }
}
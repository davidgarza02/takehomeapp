package com.davidgarza.takehome.fake

import com.davidgarza.takehome.api.ApiInterface
import com.davidgarza.takehome.data.remote.UserDetails
import com.davidgarza.takehome.repository.RemoteRepository
import com.davidgarza.takehome.util.Response
import kotlinx.coroutines.flow.flow

class FakeRemoteRepositoryImpl(
    private val api: ApiInterface = FakeApiImpl()
): RemoteRepository {

    override suspend fun searchUserRepos(username: String) = flow {
        val response = api.getUserRepos(username)
        if (response.isSuccessful){
            emit(Response.Success(response.body() ?: emptyList()))
        }else{
            emit(Response.Error("Fetch Error"))
        }
    }

    override suspend fun getUserDetails(username: String) = flow {
        val response = api.getUserDetails(username)
        if (response.isSuccessful){
            emit(Response.Success(response.body() ?: UserDetails()))
        }else{
            emit(Response.Error("Fetch Error"))
        }
    }
}
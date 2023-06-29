package com.davidgarza.takehome.repository

import com.davidgarza.takehome.data.remote.Repo
import com.davidgarza.takehome.data.remote.UserDetails
import com.davidgarza.takehome.util.Response
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    suspend fun searchUserRepos(username: String): Flow<Response<List<Repo>>>

    suspend fun getUserDetails(username: String): Flow<Response<UserDetails>>
}

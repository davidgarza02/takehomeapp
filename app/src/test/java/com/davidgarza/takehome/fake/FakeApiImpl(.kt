package com.davidgarza.takehome.fake

import com.davidgarza.takehome.api.ApiInterface
import com.davidgarza.takehome.data.remote.Repo
import com.davidgarza.takehome.data.remote.UserDetails
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeApiImpl : ApiInterface {
    override suspend fun getUserRepos(username: String): Response<List<Repo>> {
        return if (username == "ValidUsername") {
            Response.success(fakeRepoList)
        } else {
            Response.error(404, "User Not Found".toResponseBody())
        }
    }

    override suspend fun getUserDetails(username: String): Response<UserDetails> {
        return if (username == "ValidUsername") {
            Response.success(validUserDetails)
        } else {
            Response.error(404, "User Not Found".toResponseBody())
        }
    }
}
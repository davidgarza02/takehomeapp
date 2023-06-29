package com.davidgarza.takehome.fake

import com.davidgarza.takehome.data.remote.Repo
import com.davidgarza.takehome.data.remote.UserDetails

val validUserDetails = UserDetails("Michael Jackson", "https://placehold.co/160x160")
val fakeRepoList = listOf(
    Repo(idRepo = 1L, "fake repo 1", "this is a description", "2023-02-19", 100, 500),
    Repo(idRepo = 2L, "fake repo 2", "this is a description", "2023-02-19", 33, 22),
    Repo(idRepo = 3L, "fake repo 3", "this is a description", "2023-02-19", 2, 50),
    Repo(idRepo = 4L, "fake repo 4", "this is a description", "2023-02-19", 1, 100)
)
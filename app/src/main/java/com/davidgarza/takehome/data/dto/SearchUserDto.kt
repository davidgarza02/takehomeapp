package com.davidgarza.takehome.data.dto

import com.davidgarza.takehome.data.remote.Repo
import com.davidgarza.takehome.data.remote.UserDetails

data class SearchUserDto(
    val userDetails: UserDetails?,
    val repoList: List<Repo>?
)

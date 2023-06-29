package com.davidgarza.takehome.ui.searchuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidgarza.takehome.data.dto.SearchUserDto
import com.davidgarza.takehome.data.remote.Repo
import com.davidgarza.takehome.repository.RemoteRepository
import com.davidgarza.takehome.util.Constants
import com.davidgarza.takehome.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Data(null))
    val uiState: StateFlow<ViewState> = _uiState

    var totalForks = 0
    val usernameQuery = MutableLiveData<String?>()

    private suspend fun fetchUserRepos() = viewModelScope.async {
        usernameQuery.value?.let {
            remoteRepository.searchUserRepos(it)
        }
    }.await()


    private suspend fun fetchUserDetails() = viewModelScope.async {
        usernameQuery.value?.let {
            remoteRepository.getUserDetails(it)
        }
    }.await()

    fun fetchUserDetailsRepos() = viewModelScope.launch {
        val details = fetchUserDetails()
        val repos = fetchUserRepos()

        if (details != null && repos != null) {
            details.zip(repos) { detailsFlow, reposFlow ->
                when {
                    detailsFlow is Response.Success && reposFlow is Response.Success -> {
                        totalForks = reposFlow.data.sumOf { it.forks }
                        _uiState.value = ViewState.Data(
                            SearchUserDto(
                                detailsFlow.data, reposFlow.data
                            )
                        )
                    }

                    else -> _uiState.value = ViewState.Error(Constants.ERROR_FETCHING_DATA)
                }
            }.collect()
        }
    }


    fun setUsernameQuery(searchUsername: String?) = viewModelScope.launch {
        usernameQuery.value = searchUsername
    }

    sealed class ViewState {
        data class Data(val data: SearchUserDto?) : ViewState()
        data class Error(val error: String) : ViewState()
    }
}
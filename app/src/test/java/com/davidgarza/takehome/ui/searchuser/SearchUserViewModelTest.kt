package com.davidgarza.takehome.ui.searchuser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidgarza.takehome.data.dto.SearchUserDto
import com.davidgarza.takehome.util.MainDispatcherRule
import com.davidgarza.takehome.fake.FakeRemoteRepositoryImpl
import com.davidgarza.takehome.fake.fakeRepoList
import com.davidgarza.takehome.fake.validUserDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class SearchUserViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchUserViewModel

    @Before
    fun setup() {
        viewModel = SearchUserViewModel(FakeRemoteRepositoryImpl())
    }

    @Test
    fun fetchUserRepos() {
        viewModel.usernameQuery.value = "ValidUsername"
        viewModel.fetchUserDetailsRepos()

        val uiState = viewModel.uiState

        assertThat(
            uiState.value, equalTo(
                SearchUserViewModel.ViewState.Data(
                    SearchUserDto(
                        validUserDetails, fakeRepoList
                    )
                )
            )
        )
    }

    @Test
    fun setUsername_UsernameIsStore() {
        viewModel.setUsernameQuery("ValidUsername")

        assertThat(viewModel.usernameQuery.value, equalTo("ValidUsername"))
    }
}
package com.davidgarza.takehome.ui.searchuser

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import org.junit.Test
import com.davidgarza.takehome.R
import com.davidgarza.takehome.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.equalTo

@HiltAndroidTest
class SearchUserFragmentTest {

    @Test
    fun launchApplication_SearchUsersFragmentIsVisible() {
        launchFragmentInHiltContainer<SearchUserFragment>()

        onView(withId(R.id.til_search_username))
            .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        onView(withId(R.id.tiet_search_username))
            .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        onView(withId(R.id.button_search))
            .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun launchApplication_SearchResultsGroupIsGone() {
        launchFragmentInHiltContainer<SearchUserFragment>()

        onView(withId(R.id.results_group))
            .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    @Test
    fun typeTapSearchButton_resultsGroupIsVisible() {
        launchFragmentInHiltContainer<SearchUserFragment>()

        onView(withId(R.id.tiet_search_username)).perform(typeText("david"))
        onView(withId(R.id.button_search)).perform(click())

        Thread.sleep(2000)

        onView(withId(R.id.results_group)).check(
            matches(
                ViewMatchers.withEffectiveVisibility(
                    ViewMatchers.Visibility.VISIBLE
                )
            )
        )
    }

    @Test
    fun testNavigation() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        runOnUiThread {
            navController.setGraph(R.navigation.nav_graph)
        }

        launchFragmentInHiltContainer<SearchUserFragment>(navController = navController)

        onView(withId(R.id.tiet_search_username)).perform(typeText("david"))
        onView(withId(R.id.button_search)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.rv_repos)).perform(
            RecyclerViewActions.actionOnItemAtPosition<SearchRepoAdapter.SearchViewHolder>(
                0,
                click()
            )
        )

        assertThat(
            navController.currentDestination?.id.toString(),
            equalTo(R.id.detailsFragment.toString())
        )
    }
}
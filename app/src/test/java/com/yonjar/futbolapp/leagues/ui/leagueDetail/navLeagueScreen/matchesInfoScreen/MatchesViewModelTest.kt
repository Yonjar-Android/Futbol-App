package com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.matchesInfoScreen

import app.cash.turbine.test
import com.yonjar.futbolapp.TestCoroutineRule
import com.yonjar.futbolapp.leagues.data.repositories.RepositoryLeagues
import com.yonjar.futbolapp.leagues.data.repositories.motherObjects.MotherObjectLeague
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class MatchesViewModelTest{
    @get: Rule
    val dispatcherRule = TestCoroutineRule()

    @Mock
    private lateinit var repositoryLeagues: RepositoryLeagues

    private lateinit var viewModel: MatchesViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        viewModel = MatchesViewModel(repositoryLeagues)
    }

    @Test
    fun `getMatches() should return next matches successfully`() = runTest {
        // Given
        Mockito.`when`(repositoryLeagues.getMatchesByLeagueId(1,"upcoming.participants")).thenReturn(MotherObjectLeague.nextMatches)

        //When
        viewModel.getMatches(1, MatchesTabs.NextMatches)

        //Then
        viewModel.state.test {
            assertTrue(awaitItem() is MatchesState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is MatchesState.Success)
            val successState = state as MatchesState.Success
            assertEquals(successState.matchesState,MotherObjectLeague.nextMatches)
        }

    }

    @Test
    fun `getMatches() should return last matches successfully`() = runTest {
        // Given
        Mockito.`when`(repositoryLeagues.getMatchesByLeagueId(1,"latest.participants;latest.scores;latest.events")).thenReturn(MotherObjectLeague.lastMatches)

        //When
        viewModel.getMatches(1, MatchesTabs.LastsMatches)

        //Then
        viewModel.state.test {
            assertTrue(awaitItem() is MatchesState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is MatchesState.Success)
            val successState = state as MatchesState.Success
            assertEquals(successState.matchesState,MotherObjectLeague.lastMatches)
        }
    }

    @Test
    fun `getMatches() should return error state on exception`() = runTest {
        // Given
        Mockito.`when`(repositoryLeagues.getMatchesByLeagueId(1, "upcoming.participants"))
            .thenThrow(RuntimeException("Simulated error"))

        // When
        viewModel.getMatches(1, MatchesTabs.NextMatches)

        // Then
        viewModel.state.test {
            assertTrue(awaitItem() is MatchesState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is MatchesState.Error)
            assertEquals((state as MatchesState.Error).errorMessage, "Error: Simulated error")
        }
    }

    @Test
    fun `getMatches() should return null state`() = runTest {
        // Given
        Mockito.`when`(repositoryLeagues.getMatchesByLeagueId(1, "upcoming.participants"))
            .thenReturn(null)

        // When
        viewModel.getMatches(1, MatchesTabs.NextMatches)

        // Then
        viewModel.state.test {
            assertTrue(awaitItem() is MatchesState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is MatchesState.Error)
            assertEquals((state as MatchesState.Error).errorMessage, "Response was null")
        }
    }
}
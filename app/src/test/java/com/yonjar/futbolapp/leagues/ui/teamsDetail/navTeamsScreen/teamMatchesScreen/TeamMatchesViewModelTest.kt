package com.yonjar.futbolapp.leagues.ui.teamsDetail.navTeamsScreen.teamMatchesScreen

import app.cash.turbine.test
import com.yonjar.futbolapp.TestCoroutineRule
import com.yonjar.futbolapp.leagues.data.repositories.RepositoryTeams
import com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.matchesInfoScreen.MatchesTabs
import com.yonjar.futbolapp.motherObjects.MotherObjectLeague
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
class TeamMatchesViewModelTest{
    @get: Rule
    val dispatcherRule = TestCoroutineRule()

    @Mock
    private lateinit var repositoryTeams: RepositoryTeams

    private lateinit var viewModel: TeamMatchesViewModel

    private val teamId:Int = 10

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        viewModel = TeamMatchesViewModel(repositoryTeams)
    }

    @Test
    fun `getMatchesByTeamId should emit Success state with next matches`() = runTest {
        // Given
        Mockito.`when`(repositoryTeams.getMatchesByTeamId(teamId, "upcoming.participants"))
            .thenReturn(MotherObjectLeague.nextMatches)

        // When
        viewModel.getMatchesByTeamId(teamId, MatchesTabs.NextMatches)

        // Then
        viewModel.state.test {
            assertTrue(awaitItem() is TeamMatchesState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is TeamMatchesState.Success)
            val successState = state as TeamMatchesState.Success
            assertEquals(successState.matches, MotherObjectLeague.nextMatches)
        }
    }

    @Test
    fun `getMatchesByTeamId should emit Success state with last matches`() = runTest {
        // Given
        Mockito.`when`(repositoryTeams.getMatchesByTeamId(teamId, "latest.participants;latest.scores;latest.events"))
            .thenReturn(MotherObjectLeague.lastMatches)

        // When
        viewModel.getMatchesByTeamId(teamId, MatchesTabs.LastsMatches)

        // Then
        viewModel.state.test {
            assertTrue(awaitItem() is TeamMatchesState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is TeamMatchesState.Success)
            val successState = state as TeamMatchesState.Success
            assertEquals(successState.matches, MotherObjectLeague.lastMatches)
        }
    }

    @Test
    fun `getMatchesByTeamId should emit Error state when response is null`() = runTest {
        // Given
        Mockito.`when`(repositoryTeams.getMatchesByTeamId(teamId, "upcoming.participants"))
            .thenReturn(null)

        // When
        viewModel.getMatchesByTeamId(teamId, MatchesTabs.NextMatches)

        // Then
        viewModel.state.test {
            assertTrue(awaitItem() is TeamMatchesState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is TeamMatchesState.Error)
            val errorState = state as TeamMatchesState.Error
            assertEquals(errorState.errorMessage, "Response was null")
        }
    }

    @Test
    fun `getMatchesByTeamId should emit Error state when repository throws exception`() = runTest {
        // Given
        Mockito.`when`(repositoryTeams.getMatchesByTeamId(teamId, "upcoming.participants"))
            .thenThrow(RuntimeException("Service error"))

        // When
        viewModel.getMatchesByTeamId(teamId, MatchesTabs.NextMatches)

        viewModel.state.test {
            assertTrue(awaitItem() is TeamMatchesState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is TeamMatchesState.Error)
            val errorState = state as TeamMatchesState.Error
            assertEquals(errorState.errorMessage, "Error: Service error")
        }
    }

}
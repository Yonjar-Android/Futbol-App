package com.yonjar.futbolapp.leagues.ui.teamsDetail

import app.cash.turbine.test
import com.yonjar.futbolapp.TestCoroutineRule
import com.yonjar.futbolapp.leagues.data.repositories.RepositoryTeams
import com.yonjar.futbolapp.motherObjects.MotherObjectLeague
import com.yonjar.futbolapp.motherObjects.MotherObjectSquad
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
class TeamScreenViewModelTest{
    @get: Rule
    val dispatcherRule = TestCoroutineRule()

    @Mock
    private lateinit var repositoryTeams: RepositoryTeams

    private lateinit var viewModel: TeamScreenViewModel

    private val teamId = 10

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        viewModel = TeamScreenViewModel(repositoryTeams)
    }

    @Test
    fun `loadTeam should return success state when data is received`() = runTest {
        //Given
        Mockito.`when`(repositoryTeams.getTeamById(teamId)).thenReturn(MotherObjectLeague.fcCopenhagueModel)
        Mockito.`when`(repositoryTeams.getTeamSquadById(teamId)).thenReturn(MotherObjectSquad.teamSquadModel)

        //When
        viewModel.loadTeam(teamId)

        //Then
        viewModel.state.test {
            assertTrue(awaitItem() is TeamsScreenState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is TeamsScreenState.Success)
            val successState = state as TeamsScreenState.Success
            assertEquals(successState.team, MotherObjectLeague.fcCopenhagueModel)
            assertEquals(successState.squadList, MotherObjectSquad.teamSquadModel)
        }
    }

    @Test
    fun `loadTeam should return error state when data is null`() = runTest {
        //Given
        Mockito.`when`(repositoryTeams.getTeamById(teamId)).thenReturn(MotherObjectLeague.fcCopenhagueModel)
        Mockito.`when`(repositoryTeams.getTeamSquadById(teamId)).thenReturn(null)

        //When
        viewModel.loadTeam(teamId)

        viewModel.state.test {
            assertTrue(awaitItem() is TeamsScreenState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is TeamsScreenState.Error)
            val successState = state as TeamsScreenState.Error
            assertEquals(successState.errorMessage, "Response was null")
        }
    }

    @Test
    fun `loadTeam should return error state on exception`() = runTest {
        //Given
        Mockito.`when`(repositoryTeams.getTeamById(teamId)).thenReturn(MotherObjectLeague.fcCopenhagueModel)
        Mockito.`when`(repositoryTeams.getTeamSquadById(teamId)).thenThrow(RuntimeException("Simulated error"))

        //When
        viewModel.loadTeam(teamId)

        //Then
        viewModel.state.test {
            assertTrue(awaitItem() is TeamsScreenState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is TeamsScreenState.Error)
            val errorState = state as TeamsScreenState.Error
            assertEquals(errorState.errorMessage, "Error: Simulated error")
        }
    }

}
package com.yonjar.futbolapp.leagues.ui.leagueDetail

import android.annotation.SuppressLint
import app.cash.turbine.test
import com.yonjar.futbolapp.TestCoroutineRule
import com.yonjar.futbolapp.leagues.data.repositories.RepositoryLeagues
import com.yonjar.futbolapp.leagues.data.repositories.motherObjects.MotherObjectLeague
import com.yonjar.futbolapp.leagues.data.repositories.motherObjects.MotherObjectStandings
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

class DetailLeagueViewModelTest {
    @get: Rule
    val dispatcherRule = TestCoroutineRule()

    @Mock
    private lateinit var repositoryLeagues: RepositoryLeagues

    private lateinit var viewModel: DetailLeagueViewModel

    private val leagueId = 1
    @Before

    fun setUp(){
        MockitoAnnotations.openMocks(this)
        viewModel = DetailLeagueViewModel(repositoryLeagues)
    }

    @SuppressLint("CheckResult")
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `chargeLeague should return success state`() = runTest {
        //Given
        Mockito.`when`(repositoryLeagues.getLeagueById(leagueId)).thenReturn(MotherObjectLeague.leagueModel)
        val seasonId = MotherObjectLeague.leagueModel.currentSeason?.idSeason
        Mockito.`when`(repositoryLeagues.getStandingBySeasonId(seasonId)).thenReturn(MotherObjectStandings.leaguesModelList)
        Mockito.`when`(repositoryLeagues.getStandingPlayOffOneBySeasonId(seasonId)).thenReturn(MotherObjectStandings.leaguesModelList)

        //When
        viewModel.chargeLeague(leagueId)

        //Then
        viewModel.state.test {
            assertTrue(awaitItem() is DetailLeagueState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is DetailLeagueState.Success)
            val successState = state as DetailLeagueState.Success
            assertEquals(successState.league, MotherObjectLeague.leagueModel)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `chargeLeague should return error state`() = runTest {
        // Given
        Mockito.`when`(repositoryLeagues.getLeagueById(leagueId)).thenReturn(MotherObjectLeague.leagueModel)
        val seasonId = MotherObjectLeague.leagueModel.currentSeason?.idSeason
        Mockito.`when`(repositoryLeagues.getStandingBySeasonId(seasonId)).thenReturn(MotherObjectStandings.leaguesModelList)
        Mockito.`when`(repositoryLeagues.getStandingPlayOffOneBySeasonId(seasonId)).thenThrow(RuntimeException("Simulated error"))

        // When
        viewModel.chargeLeague(leagueId)

        // Then
        viewModel.state.test {
            assertTrue(awaitItem() is DetailLeagueState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is DetailLeagueState.Error)
            val errorState = state as DetailLeagueState.Error
            assertEquals(errorState.errorMessage, "Error: Simulated error")
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `chargeLeague should return error state on null response`() = runTest {
        // Given
        Mockito.`when`(repositoryLeagues.getLeagueById(leagueId)).thenReturn(MotherObjectLeague.leagueModel)
        val seasonId = MotherObjectLeague.leagueModel.currentSeason?.idSeason
        Mockito.`when`(repositoryLeagues.getStandingBySeasonId(seasonId)).thenReturn(null)

        //When
        viewModel.chargeLeague(leagueId)

        //Given
        viewModel.state.test {
            assertTrue(awaitItem() is DetailLeagueState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is DetailLeagueState.Error)
            val errorState = state as DetailLeagueState.Error
            assertEquals(errorState.errorMessage, "Response was null")
        }

    }
}
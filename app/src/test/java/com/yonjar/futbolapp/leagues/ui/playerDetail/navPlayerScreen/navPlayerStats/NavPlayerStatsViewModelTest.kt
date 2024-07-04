package com.yonjar.futbolapp.leagues.ui.playerDetail.navPlayerScreen.navPlayerStats

import app.cash.turbine.test
import com.yonjar.futbolapp.TestCoroutineRule
import com.yonjar.futbolapp.leagues.data.repositories.RepositoryTeams
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
class NavPlayerStatsViewModelTest{
    @get: Rule
    val dispatcherRule = TestCoroutineRule()

    @Mock
    private lateinit var repositoryTeams: RepositoryTeams

    private lateinit var viewModel: NavPlayerStatsViewModel

    private val playerId = 10
    private val seasonId = 825

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        viewModel = NavPlayerStatsViewModel(repositoryTeams)
    }

    @Test
    fun `getPlayerStatistics should return immediately when actual player is the same`() = runTest {
        // Given
        Mockito.`when`(repositoryTeams.getPlayerStatistics(playerId, seasonId)).thenReturn(
            MotherObjectSquad.playerStatistics)
        viewModel.getPlayerStatistics(playerId, seasonId)

        // Then
        viewModel.state.test {
            assertTrue(awaitItem() is NavPlayerStatsState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is NavPlayerStatsState.Success)
        }

        // Verify the repository method was called once
        Mockito.verify(repositoryTeams, Mockito.times(1)).getPlayerStatistics(playerId, seasonId)

        // When
        viewModel.getPlayerStatistics(playerId, seasonId)
        advanceUntilIdle()

        // Verify the repository method was still called only once
        Mockito.verify(repositoryTeams, Mockito.times(1)).getPlayerStatistics(playerId, seasonId)

    }

    @Test
    fun `getPlayerStatistics should return success state when data is available`() = runTest {
        //Given
        Mockito.`when`(repositoryTeams.getPlayerStatistics(playerId,seasonId)).thenReturn(
            MotherObjectSquad.playerStatistics)

        //When
        viewModel.getPlayerStatistics(playerId,seasonId)

        viewModel.state.test {
            assertTrue(awaitItem() is NavPlayerStatsState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is NavPlayerStatsState.Success)
            val successState = state as NavPlayerStatsState.Success
            assertEquals(successState.list, MotherObjectSquad.playerStatistics)
        }
    }

    @Test
    fun `getPlayerStatistics should return error state when response is null`() = runTest {
        // Given
        Mockito.`when`(repositoryTeams.getPlayerStatistics(playerId, seasonId)).thenReturn(null)

        // When
        viewModel.getPlayerStatistics(playerId, seasonId)


        // Then
        viewModel.state.test {
            assertTrue(awaitItem() is NavPlayerStatsState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is NavPlayerStatsState.Error)
            val errorState = state as NavPlayerStatsState.Error
            assertEquals(errorState.errorMessage, "Response was null")
        }
    }

    @Test
    fun `getPlayerStatistics should return error state when exception is thrown`() = runTest {
        // Given
        Mockito.`when`(repositoryTeams.getPlayerStatistics(playerId, seasonId)).thenThrow(RuntimeException("Simulated error"))

        // When
        viewModel.getPlayerStatistics(playerId, seasonId)


        // Then
        viewModel.state.test {
            assertTrue(awaitItem() is NavPlayerStatsState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is NavPlayerStatsState.Error)
            val errorState = state as NavPlayerStatsState.Error
            assertEquals(errorState.errorMessage, "Error: Simulated error")
        }
    }

    @Test
    fun `areStatsLoaded should return actual playerId`() = runTest {
        // Given
        Mockito.`when`(repositoryTeams.getPlayerStatistics(playerId, seasonId)).thenReturn(
            MotherObjectSquad.playerStatistics)

        // When
        viewModel.getPlayerStatistics(playerId, seasonId)
        advanceUntilIdle()

        // Then
        assertEquals(viewModel.areStatsLoaded(), playerId)
    }
}
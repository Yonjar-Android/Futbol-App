package com.yonjar.futbolapp.leagues.ui.playerDetail

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
class PlayerScreenViewModelTest{
    @get: Rule
    val dispatcherRule = TestCoroutineRule()

    @Mock
    private lateinit var repositoryTeams: RepositoryTeams

    private lateinit var viewModel:PlayerScreenViewModel

    private val playerId = 758

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        viewModel = PlayerScreenViewModel(repositoryTeams)
    }

    @Test
    fun `getPlayerById should return a success state`() = runTest {
        //Given
        Mockito.`when`(repositoryTeams.getPlayerById(playerId)).thenReturn(MotherObjectSquad.playerModel)

        //When
        viewModel.getPlayerInformation(playerId)

        viewModel.state.test {
            assertTrue(awaitItem() is PlayerState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is PlayerState.Success)
            val successState = state as PlayerState.Success
            assertEquals(successState.player, MotherObjectSquad.playerModel)
        }
    }

    @Test
    fun `getPlayerById should return error state on null response`() = runTest {
        //Given
        Mockito.`when`(repositoryTeams.getPlayerById(playerId)).thenReturn(null)

        //When
        viewModel.getPlayerInformation(playerId)

        //Then
        viewModel.state.test {
            assertTrue(awaitItem() is PlayerState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is PlayerState.Error)
            val errorState = state as PlayerState.Error
            assertEquals(errorState.errorMessage, "Response was null")
        }
    }

    @Test
    fun `getPlayerById should return error state on exception`() = runTest {
        //Given
        Mockito.`when`(repositoryTeams.getPlayerById(playerId)).thenThrow(RuntimeException("Simulated error"))

        //When
        viewModel.getPlayerInformation(playerId)

        //Then
        viewModel.state.test {
            assertTrue(awaitItem() is PlayerState.Loading)
            advanceUntilIdle()
            val state = awaitItem()
            assertTrue(state is PlayerState.Error)
            val errorState = state as PlayerState.Error
            assertEquals(errorState.errorMessage, "Error: Simulated error")
        }
    }
}
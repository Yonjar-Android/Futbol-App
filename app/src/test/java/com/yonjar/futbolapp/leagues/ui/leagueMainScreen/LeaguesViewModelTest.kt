import app.cash.turbine.test
import com.yonjar.futbolapp.TestCoroutineRule
import com.yonjar.futbolapp.leagues.data.repositories.RepositoryLeagues
import com.yonjar.futbolapp.leagues.data.repositories.motherObjects.MotherObjectLeague
import com.yonjar.futbolapp.leagues.ui.leagueMainScreen.LeaguesState
import com.yonjar.futbolapp.leagues.ui.leagueMainScreen.LeaguesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class LeaguesViewModelTest {

    @get:Rule
    val dispatcherRule = TestCoroutineRule()

    @Mock
    private lateinit var repositoryLeagues: RepositoryLeagues

    private lateinit var viewModel: LeaguesViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `LeaguesViewModel should change the state to Success`() = runTest {
        // Given
        Mockito.`when`(repositoryLeagues.getLeagues()).thenReturn(MotherObjectLeague.leagues)

        // When
        viewModel = LeaguesViewModel(repositoryLeagues)

        // Then
        viewModel.state.test {
            assertTrue(awaitItem() is LeaguesState.Loading) // Verifica que el estado inicial es Loading
            advanceUntilIdle() // Avanza el dispatcher hasta que no haya más tareas pendientes
            val state = awaitItem()
            assertTrue(state is LeaguesState.Success) // Verifica que el estado cambia a Success
            val successState = state as LeaguesState.Success
            assertEquals(successState.leagues, MotherObjectLeague.leagues)
        }
    }

    @Test
    fun `LeaguesViewModel should change the state to Error`() = runTest {
        // Given
        Mockito.`when`(repositoryLeagues.getLeagues()).thenReturn(null) // Simula una respuesta vacía

        // When
        viewModel = LeaguesViewModel(repositoryLeagues)

        // Then
        viewModel.state.test {
            assertTrue(awaitItem() is LeaguesState.Loading) // Verifica que el estado inicial es Loading
            advanceUntilIdle() // Avanza el dispatcher hasta que no haya más tareas pendientes
            val state = awaitItem()
            assertTrue(state is LeaguesState.Error) // Verifica que el estado cambia a Error
            val errorState = state as LeaguesState.Error
            assertEquals(errorState.errorMessage, "Error cargando")
        }
    }

}

package com.yonjar.futbolapp.leagues.data.repositories

import com.yonjar.futbolapp.leagues.data.network.LeagueService
import com.yonjar.futbolapp.leagues.domain.models.MatchModel
import com.yonjar.futbolapp.leagues.domain.models.leagueModels.LeagueModel
import com.yonjar.futbolapp.leagues.domain.models.leagueModels.StandingModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times

class RepositoryLeaguesTest {

    @Mock
    lateinit var leagueService: LeagueService

    private lateinit var repositoryLeagues: RepositoryLeagues

    private val id = 1
    private val seasonId: Int = 21644
    private val seasonIdTwo: Int = 19686
    val leagueId:Int = 271

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repositoryLeagues = RepositoryLeagues(leagueService)
    }

    @Test
    fun `getLeagues should return a list of LeagueModel from API`() = runBlocking {
        // Given
        Mockito.`when`(leagueService.getLeagues()).thenReturn(MotherObjectLeagueResponse.leagueResponse)

        // When
        val response: List<LeagueModel>? = repositoryLeagues.getLeagues()

        // Then
        Mockito.verify(leagueService, times(1)).getLeagues()
        assertNotNull(response)
        assertTrue(response == MotherObjectLeagueResponse.leagueResponse.leagues.map { it.toLeagueModel() })
    }

    @Test
    fun `getLeagues should return null on API failure`() = runBlocking {
        // Given
        Mockito.`when`(leagueService.getLeagues()).thenThrow(RuntimeException("API Error"))

        // When
        val response: List<LeagueModel>? = repositoryLeagues.getLeagues()

        // Then
        Mockito.verify(leagueService, times(1)).getLeagues()
        assertNull(response)
    }

    @Test
    fun `getLeagueById should return a LeagueModel`() = runBlocking {
        // Given
        Mockito.`when`(leagueService.getLeagueById(id)).thenReturn(MotherObjectLeagueResponse.oneLeagueResponse)

        // When
        val response: LeagueModel? = repositoryLeagues.getLeagueById(id)

        // Then
        Mockito.verify(leagueService, times(1)).getLeagueById(id)
        assertNotNull(response)
        assertTrue(response is LeagueModel)
        assertEquals(response, MotherObjectLeagueResponse.oneLeagueResponse.league.toLeagueModel())
    }

    @Test
    fun `getLeagueById should return null on API failure`() = runBlocking {
        // Given
        Mockito.`when`(leagueService.getLeagueById(id)).thenThrow(RuntimeException("API Error"))

        // When
        val response: LeagueModel? = repositoryLeagues.getLeagueById(id)

        // Then
        Mockito.verify(leagueService, times(1)).getLeagueById(id)
        assertNull(response)
    }

    @Test
    fun `getStandingBySeasonId should return a list of StandingModel for a specific season`() = runBlocking {
        // Given
        Mockito.`when`(leagueService.getStandingsBySeasonId(seasonId)).thenReturn(MotherObjectStandings.standingResponse)

        // When
        val response: List<StandingModel>? = repositoryLeagues.getStandingBySeasonId(seasonId)

        // Then
        Mockito.verify(leagueService, times(1)).getStandingsBySeasonId(seasonId)
        assertNotNull(response)
        assertEquals(response?.get(0)?.seasonId, MotherObjectStandings.standingResponse.data[0].toStandingModel().seasonId)
    }

    @Test
    fun `getStandingBySeasonId should return null on API failure`() = runBlocking {
        // Given
        Mockito.`when`(leagueService.getStandingsBySeasonId(seasonId)).thenThrow(RuntimeException("API Error"))

        // When
        val response: List<StandingModel>? = repositoryLeagues.getStandingBySeasonId(seasonId)

        // Then
        Mockito.verify(leagueService, times(1)).getStandingsBySeasonId(seasonId)
        assertNull(response)
    }

    @Test
    fun `getStandingPlayOffOneBySeasonId should return filtered standings by Championship or Relegation Round`() = runBlocking {
        // Given
        Mockito.`when`(leagueService.getStandingsBySeasonId(seasonIdTwo)).thenReturn(MotherObjectStandings.mockStandingResponse)

        // When
        val response: List<StandingModel>? = repositoryLeagues.getStandingPlayOffOneBySeasonId(seasonIdTwo)

        // Then
        Mockito.verify(leagueService, times(1)).getStandingsBySeasonId(seasonIdTwo)
        assertNotNull(response)
        assertEquals(response?.get(0)?.seasonId, MotherObjectStandings.mockStandingResponse.data[0].seasonId)
    }

    @Test
    fun `getStandingPlayOffOneBySeasonId should return null on API failure`() = runBlocking {
        // Given
        Mockito.`when`(leagueService.getStandingsBySeasonId(seasonIdTwo)).thenThrow(RuntimeException("API Error"))

        // When
        val response: List<StandingModel>? = repositoryLeagues.getStandingPlayOffOneBySeasonId(seasonIdTwo)

        // Then
        Mockito.verify(leagueService, times(1)).getStandingsBySeasonId(seasonIdTwo)
        assertNull(response)
    }

    @Test
    fun `getMatchesByLeagueId should return upcoming matches when include is upcoming_participants`()= runBlocking {
        // Given
        Mockito.`when`(leagueService.getMatchesByLeagueId(leagueId,"upcoming.participants")).thenReturn(MotherObjectLeagueResponse.oneLeagueResponse)

        // When
        val response: List<MatchModel>? = repositoryLeagues.getMatchesByLeagueId(leagueId,"upcoming.participants")

        // Then
        Mockito.verify(leagueService, times(1)).getMatchesByLeagueId(leagueId, "upcoming.participants")
        assertNotNull(response)
        assertEquals(response?.get(0)?.name, MotherObjectLeagueResponse.oneLeagueResponse.league.toLeagueModel().upcomingMatches?.get(0)?.name)
        assertTrue(response is List<MatchModel>)
    }

    @Test
    fun `getMatchesByLeagueId should return latest matches when include is latest_participants_latest_scores_latest_events`() = runBlocking {
        // Given
        Mockito.`when`(leagueService.getMatchesByLeagueId(leagueId, "latest.participants;latest.scores;latest.events"))
            .thenReturn(MotherObjectLeagueResponse.oneLeagueResponse)

        // When
        val response: List<MatchModel>? = repositoryLeagues.getMatchesByLeagueId(leagueId, "latest.participants;latest.scores;latest.events")

        // Then
        Mockito.verify(leagueService, times(1)).getMatchesByLeagueId(leagueId, "latest.participants;latest.scores;latest.events")
        assertNotNull(response)
        assertEquals(response?.get(0)?.name, MotherObjectLeagueResponse.oneLeagueResponse.league.latestMatches?.get(0)?.name)
        assertTrue(response is List<MatchModel>)
    }

    @Test
    fun `getMatchesByLeagueId should return null on API failure`() = runBlocking {
        // Given
        Mockito.`when`(leagueService.getMatchesByLeagueId(leagueId)).thenThrow(RuntimeException("API Error"))

        // When
        val response: List<MatchModel>? = repositoryLeagues.getMatchesByLeagueId(leagueId,"upcoming.participants")

        //Then
        Mockito.verify(leagueService, times(1)).getMatchesByLeagueId(leagueId)
        assertNull(response)
    }
}

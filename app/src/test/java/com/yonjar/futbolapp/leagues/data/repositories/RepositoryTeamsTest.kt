package com.yonjar.futbolapp.leagues.data.repositories

import com.yonjar.futbolapp.leagues.data.network.TeamService
import com.yonjar.futbolapp.motherObjects.MotherObjectLeague
import com.yonjar.futbolapp.motherObjects.MotherObjectSquad
import com.yonjar.futbolapp.leagues.domain.models.PlayerModel
import com.yonjar.futbolapp.leagues.domain.models.PlayerStatistics
import com.yonjar.futbolapp.leagues.domain.models.teamModels.TeamModel
import com.yonjar.futbolapp.leagues.domain.models.teamModels.TeamSquadModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times

class RepositoryTeamsTest{
    @Mock
    lateinit var teamService: TeamService

    private lateinit var repositoryTeams: RepositoryTeams

    private val teamId = 10
    private val playerId = 758
    private val seasonId = 825
    private val exception = RuntimeException("Network error")

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repositoryTeams = RepositoryTeams(teamService)
    }

    @Test
    fun `repositoryTeams getTeamById should return a valid TeamModel object given a valid teamId`() = runBlocking {
        //Given
        Mockito.`when`(teamService.getTeamById(teamId)).thenReturn(MotherObjectLeague.copenhagueResponse)

        //When
        val response: TeamModel? = repositoryTeams.getTeamById(teamId)

        //Then
        assertNotNull(response)
        assertEquals(teamId, response?.id)
        assertTrue(response is TeamModel)
    }

    @Test
    fun `repositoryTeams getTeamById should return null if the service call fails`() = runBlocking {
        // Given
        Mockito.`when`(teamService.getTeamById(teamId)).thenThrow(exception)

        // When
        val response: TeamModel? = repositoryTeams.getTeamById(teamId)

        // Then
        assertNull(response)
    }

    @Test
    fun `repositoryTeams getTeamSquadById should return a list of TeamSquadModel when service call is successful`() = runBlocking {
        //Given
        Mockito.`when`(teamService.getTeamSquadById(teamId)).thenReturn(MotherObjectSquad.teamSquadResponse)

        //When
        val response:List<TeamSquadModel>? = repositoryTeams.getTeamSquadById(teamId)

        //Then
        Mockito.verify(teamService, times(1)).getTeamSquadById(teamId)
        assertNotNull(response)
        assertTrue(response is List<TeamSquadModel>)
    }

    @Test
    fun `repositoryTeams getTeamSquadById should return null if the service call fails`() = runBlocking {
        // Given
        Mockito.`when`(teamService.getTeamSquadById(teamId)).thenThrow(exception)

        // When
        val response: List<TeamSquadModel>?  = repositoryTeams.getTeamSquadById(teamId)

        // Then
        Mockito.verify(teamService, times(1)).getTeamSquadById(teamId)
        assertNull(response)
    }

    @Test
    fun `repositoryTeams getPlayerById should return a PlayerModel when service call is successful`() = runBlocking {
        //Given
        Mockito.`when`(teamService.getPlayerById(playerId)).thenReturn(MotherObjectSquad.playerResponse)

        //When
        val response:PlayerModel? = repositoryTeams.getPlayerById(playerId)

        //Then
        Mockito.verify(teamService, times(1)).getPlayerById(playerId)
        assertNotNull(response)
        assertTrue(response is PlayerModel)
        assertEquals(playerId, response?.playerId)
    }

    @Test
    fun `repositoryTeams getPlayerById should return null if the service call fails`() = runBlocking {
        // Given
        Mockito.`when`(teamService.getPlayerById(playerId)).thenThrow(exception)

        // When
        val response: PlayerModel?  = repositoryTeams.getPlayerById(playerId)

        // Then
        Mockito.verify(teamService, times(1)).getPlayerById(playerId)
        assertNull(response)
    }

    @Test
    fun `repositoryTeams getPlayerStatistics should return a PlayerStatistics when service call is successful`() = runBlocking {
        //Given
        Mockito.`when`(teamService.getPlayerById(playerId,"statistics.details.type")).thenReturn(
            MotherObjectSquad.playerResponse)

        //When
        val response:PlayerStatistics? = repositoryTeams.getPlayerStatistics(playerId,seasonId)

        //Then
        Mockito.verify(teamService, times(1)).getPlayerById(playerId,"statistics.details.type")
        assertNotNull(response)
        assertTrue(response is PlayerStatistics)
    }

    @Test
    fun `repositoryTeams getPlayerStatistics should return null if the service call fails`() = runBlocking {
        // Given
        Mockito.`when`(teamService.getPlayerById(playerId,"statistics.details.type")).thenThrow(exception)

        // When
        val response: PlayerStatistics?  = repositoryTeams.getPlayerStatistics(playerId,seasonId)

        // Then
        Mockito.verify(teamService, times(1)).getPlayerById(playerId,"statistics.details.type")
        assertNull(response)
    }
}
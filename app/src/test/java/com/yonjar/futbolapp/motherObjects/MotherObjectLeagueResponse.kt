package com.yonjar.futbolapp.motherObjects

import com.yonjar.futbolapp.leagues.data.models.CountryResponse
import com.yonjar.futbolapp.leagues.data.models.VenueResponse
import com.yonjar.futbolapp.leagues.data.models.leagueModel.CurrentSeasonModelResponse
import com.yonjar.futbolapp.leagues.data.models.leagueModel.LeagueModelResponse
import com.yonjar.futbolapp.leagues.data.models.leagueModel.LeagueResponse
import com.yonjar.futbolapp.leagues.data.models.leagueModel.OneLeagueResponse
import com.yonjar.futbolapp.leagues.data.models.matchesModel.MatchModelResponse
import com.yonjar.futbolapp.leagues.data.models.teamsModel.ActiveSeason
import com.yonjar.futbolapp.leagues.data.models.teamsModel.TeamModelResponse
import com.yonjar.futbolapp.leagues.data.models.teamsModel.TeamResponse

object MotherObjectLeagueResponse {
    private val currentSeason = CurrentSeasonModelResponse(
        idSeason = 500,
        name = "2024/2025",
        finished = false,
        isCurrent = true,
        startingAt = "2024-01-01",
        endingAt = "2024-12-31"
    )

    private val denmark = CountryResponse(
        countryId = 10,
        countryName = "Denmark",
        countryFlag = "DenmarkFlag.jpg"
    )

    private val randersVenue = VenueResponse(
        venueId = 111,
        stadiumImage = "StadiumRanders.jpg",
        stadiumName = "Randers Stadion",
        cityName = "Randers"
    )

    private val copenhagueVenue = VenueResponse(
        venueId = 100,
        stadiumImage = "StadiumCopenhague.jpg",
        stadiumName = "Parken Stadion",
        cityName = "Copenhague",
    )

    private val agfVenue = VenueResponse(
        venueId = 1708,
        stadiumImage = "StadiumAGF.jpg",
        stadiumName = "Ceres Park",
        cityName = "Århus",
    )

    private val activeSeasons = listOf(
        ActiveSeason(
            seasonId = 500,
            name = "2024/2025"
        )
    )

    private val randers = TeamModelResponse(
        id = 11,
        countryId = 10,
        yearFounded = 2003,
        teamImage = "RandersLogo.jpg",
        shortName = "Randers",
        name = "Randers",
        country = denmark,
        venue = randersVenue,
        activeSeason = activeSeasons,
        upcomingMatches = null,
        latestMatches = null
    )

    private val fcCopenhague = TeamModelResponse(
        id = 10,
        countryId = 10,
        yearFounded = 1992,
        teamImage = "CopenhagueLogo.jpg",
        shortName = "Copenhague",
        name = "FC Copenhague",
        country = denmark,
        venue = copenhagueVenue,
        activeSeason = activeSeasons,
        upcomingMatches = null,
        latestMatches = null
    )

    private val agf = TeamModelResponse(
        id = 2905,
        countryId = 10,
        yearFounded = 1902,
        teamImage = "AGF.jpg",
        shortName = "AGF",
        name = "AGF",
        country = denmark,
        venue = agfVenue,
        activeSeason = activeSeasons,
        upcomingMatches = null,
        latestMatches = null
    )


    private val teams =  listOf(
        fcCopenhague, randers
    )

    private val teamsTwo = listOf(
        fcCopenhague, agf
    )

    private val upcomingMatches = listOf(
        MatchModelResponse(
            id = 2000,
            name = "FC Copenhague vs Randers",
            startingAt = "2024-04-15",
            result = "FC Copenhague won after full time",
            scores = null,
            events = null,
            teams = teams
        )
    )

   private val lastMatches = listOf(
        MatchModelResponse(
            id = 1000,
            name = "FC Copenhague vs AGF",
            startingAt = "2024-04-15",
            result = "FC Copenhague won after full time",
            scores = null,
            events = null,
            teams = teamsTwo
        )
    )

    val copenhagueResponse = TeamResponse(
        TeamModelResponse(
            id = 10,
            countryId = 10,
            yearFounded = 1992,
            teamImage = "CopenhagueLogo.jpg",
            shortName = "Copenhague",
            name = "FC Copenhague",
            activeSeason = null,
            venue = null,
            country = null,
            upcomingMatches = upcomingMatches,
            latestMatches = lastMatches
        )
    )

     val leagueModelResponse = LeagueModelResponse(
        id = 1,
        countryId = 10,
        name = "SuperLiga",
        leagueImage = "SuperLiga.jpg",
        subType = "domestic",
        currentSeason = currentSeason,
        upcomingMatches = upcomingMatches,
        latestMatches = lastMatches
    )

    val leagueResponse = LeagueResponse(
        leagues = listOf(leagueModelResponse)
    )

    val oneLeagueResponse = OneLeagueResponse(
        league = leagueModelResponse
    )
}
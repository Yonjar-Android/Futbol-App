package com.yonjar.futbolapp.leagues.data.repositories.motherObjects

import com.yonjar.futbolapp.leagues.data.models.teamsModel.TeamModelResponse
import com.yonjar.futbolapp.leagues.data.models.teamsModel.TeamResponse
import com.yonjar.futbolapp.leagues.domain.models.CurrentSeasonModel
import com.yonjar.futbolapp.leagues.domain.models.EventModel
import com.yonjar.futbolapp.leagues.domain.models.MatchModel
import com.yonjar.futbolapp.leagues.domain.models.leagueModels.LeagueModel
import com.yonjar.futbolapp.leagues.domain.models.teamModels.TeamModel

object MotherObjectLeague {

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
            country = null
        )
    )

    private val fcCopenhague = TeamModel(
        id = 10,
        countryId = 10,
        countryFlag = "DenmarkFlag.jpg",
        cityName = "Copenhague",
        countryName = "Denmark",
        currentSeasonName = "2024/2025",
        currentSeasonId = 500,
        yearFounded = 1992,
        teamImage = "CopenhagueLogo.jpg",
        shortName = "Copenhague",
        stadiumImage = "StadiumCopenhague.jpg",
        stadiumName = "Parken Stadion",
        name = "FC Copenhague",
    )

    private val fcRanders = TeamModel(
        id = 11,
        countryId = 10,
        countryFlag = "DenmarkFlag.jpg",
        cityName = "Randers",
        countryName = "Denmark",
        currentSeasonName = "2024/2025",
        currentSeasonId = 500,
        yearFounded = 2003,
        teamImage = "RandersLogo.jpg",
        shortName = "Randers",
        stadiumImage = "StadiumRanders.jpg",
        stadiumName = "Randers Stadion",
        name = "Randers",
    )

    private val listOfEventGoalsNextM = listOf(
        EventModel(
            id = 1000,
            player = "Emil Hojlund",
            addition = "1st Goal",
            minute = 55,
            participant = 10
        )
    )

    private val listOfEventGoalsLastM = listOf(
        EventModel(
            id = 1010,
            player = "Emil Hojlund",
            addition = "1st Goal",
            minute = 15,
            participant = 10
        ),
        EventModel(
            id = 1011,
            player = "Emil Hojlund",
            addition = "2nd Goal",
            minute = 60,
            participant = 10
        )

    )

     val nextMatches = listOf(
        MatchModel(
            id = 2000,
            name = "FC Copenhague vs Randers",
            date = "2024-02-14",
            result = "FC Copenhague won after full time",
            teamHome = fcCopenhague,
            teamAway = fcRanders,
            goalsHome = 1,
            goalsAway = 0,
            goalMinutes = listOfEventGoalsNextM
        )
    )

     val lastMatches = listOf(
            MatchModel(
                id = 1000,
                name = "FC Copenhague vs Randers",
                date = "2024-04-15",
                result = "FC Copenhague won after full time",
                teamHome = fcCopenhague,
                teamAway = fcRanders,
                goalsHome = 1,
                goalsAway = 0,
                goalMinutes = listOfEventGoalsLastM
            )
    )

    val leagueModel = LeagueModel(
        id = 1,
        countryId = 10,
        "SuperLiga",
        "SuperLiga.jpg",
        subType = "domestic",
        currentSeason = CurrentSeasonModel(
            idSeason = 500,
            name = "2024/2025",
            isCurrent = true,
            startingAt = "2024-01-01",
            endingAt = "2024-12-31",
            finished = false
        ),
        upcomingMatches = nextMatches,
        latestMatches = lastMatches
    )

    val leagues = listOf(
        leagueModel
    )



}
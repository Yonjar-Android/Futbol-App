package com.yonjar.futbolapp.motherObjects

import com.yonjar.futbolapp.leagues.data.models.leagueModel.StageModelResponse
import com.yonjar.futbolapp.leagues.data.models.teamsModel.StandingModelResponse
import com.yonjar.futbolapp.leagues.data.models.teamsModel.StandingResponse
import com.yonjar.futbolapp.leagues.data.models.teamsModel.TeamModelResponse

object MotherObjectStandings {
    val standingResponse = StandingResponse(
        data = listOf(
            StandingModelResponse(
                id = 226008,
                participantId = 2356,
                sportId = 1,
                leagueId = 271,
                seasonId = 21644,
                stageId = 77463995,
                groupId = null,
                roundId = 336028,
                standingRuleId = 109391,
                position = 1,
                result = "equal",
                points = 41,
                team = TeamModelResponse(
                    id = 2356,
                    countryId = 320,
                    name = "Randers",
                    shortName = "RDF",
                    teamImage = "https://cdn.sportmonks.com/images/soccer/teams/20/2356.png",
                    yearFounded = 2003,
                    country = null,
                    venue = null,
                    activeSeason = null
                ),
                stage = StageModelResponse(
                    id = 77463995,
                    typeId = 223,
                    name = "Relegation Round"
                ),
                group = null,
                details = null
            ),
            StandingModelResponse(
                id = 201742,
                participantId = 939,
                sportId = 1,
                leagueId = 271,
                seasonId = 21644,
                stageId = 77463996,
                groupId = null,
                roundId = 307063,
                standingRuleId = 109379,
                position = 1,
                result = "equal",
                points = 48,
                team = TeamModelResponse(
                    id = 939,
                    countryId = 320,
                    name = "Midtjylland",
                    shortName = "FCM",
                    teamImage = "https://cdn.sportmonks.com/images/soccer/teams/11/939.png",
                    yearFounded = 1999,
                    country = null,
                    venue = null,
                    activeSeason = null
                ),
                stage = StageModelResponse(
                    id = 77463996,
                    typeId = 223,
                    name = "Regular Season"
                ),
                group = null,
                details = null
            ),
            // Añade más StandingModelResponse aquí según sea necesario
        )
    )

    val mockStandingResponse = StandingResponse(
        data = listOf(
            StandingModelResponse(
                id = 191109,
                participantId = 85,
                sportId = 1,
                leagueId = 271,
                seasonId = 19686,
                stageId = 77457694,
                groupId = null,
                roundId = 303148,
                standingRuleId = 12898,
                position = 1,
                result = "equal",
                points = 59,
                team = TeamModelResponse(
                    id = 85,
                    countryId = 320,
                    name = "FC Copenhagen",
                    shortName = "COP",
                    teamImage = "https://cdn.sportmonks.com/images/soccer/teams/21/85.png",
                    yearFounded = 1992,
                    country = null,
                    venue = null,
                    activeSeason = null
                ),
                stage = StageModelResponse(
                    id = 77457694,
                    typeId = 223,
                    name = "Championship Round"
                ),
                group = null,
                details = null
            ),
            StandingModelResponse(
                id = 191098,
                participantId = 939,
                sportId = 1,
                leagueId = 271,
                seasonId = 19686,
                stageId = 77457695,
                groupId = null,
                roundId = 303159,
                standingRuleId = 12894,
                position = 1,
                result = "equal",
                points = 51,
                team = TeamModelResponse(
                    id = 939,
                    countryId = 320,
                    name = "Midtjylland",
                    shortName = "FCM",
                    teamImage = "https://cdn.sportmonks.com/images/soccer/teams/11/939.png",
                    yearFounded = 1999,
                    country = null,
                    venue = null,
                    activeSeason = null
                ),
                stage = StageModelResponse(
                    id = 77457695,
                    typeId = 223,
                    name = "Relegation Round"
                ),
                group = null,
                details = null
            )
        )
    )

    val leaguesModelList = mockStandingResponse.data.map { it.toStandingModel() }


}
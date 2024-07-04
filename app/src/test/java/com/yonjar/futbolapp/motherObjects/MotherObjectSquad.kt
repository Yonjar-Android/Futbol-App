package com.yonjar.futbolapp.motherObjects

import com.yonjar.futbolapp.leagues.data.models.teamsModel.DetailedPositionResponse
import com.yonjar.futbolapp.leagues.data.models.teamsModel.Nationality
import com.yonjar.futbolapp.leagues.data.models.teamsModel.PlayerData
import com.yonjar.futbolapp.leagues.data.models.teamsModel.PlayerModelResponse
import com.yonjar.futbolapp.leagues.data.models.teamsModel.PlayerStatDetail
import com.yonjar.futbolapp.leagues.data.models.teamsModel.PlayerStatDetailType
import com.yonjar.futbolapp.leagues.data.models.teamsModel.PlayerStatDetailValue
import com.yonjar.futbolapp.leagues.data.models.teamsModel.PlayerStatisticResponse
import com.yonjar.futbolapp.leagues.data.models.teamsModel.PositionModelResponse
import com.yonjar.futbolapp.leagues.data.models.teamsModel.TeamSquadModelResponse
import com.yonjar.futbolapp.leagues.data.models.teamsModel.TeamSquadResponse
import com.yonjar.futbolapp.leagues.domain.models.PlayerStatistics

object MotherObjectSquad {

    private val jamesTavernier = PlayerModelResponse(
        id = 758,
        nationality = Nationality(
            id = 462,
            countryName = "England",
            flagImage = "https://cdn.sportmonks.com/images/soccer/teams/12/462.png"
        ),
        positionId = 25,
        displayName = "James Tavernier",
        playerImage = "https://cdn.sportmonks.com/images/soccer/players/22/758.png",
        height = 182,
        weight = 75,
        gender = "male",
        position = PositionModelResponse(
            id = 25,
            name = "Defender"
        ),
        detailedPosition = DetailedPositionResponse(
            id = 154,
            name = "Right Back"
        ),
        dateOfBirth = "1991-10-31",
        statistics = listOf(
            PlayerStatisticResponse(
                seasonId = 825,
                details = listOf(
                    PlayerStatDetail(
                        value = PlayerStatDetailValue(totalValue = 1),
                        type = PlayerStatDetailType(
                            name = "Goals",
                            developerName = "GOALS"
                        )
                    ),
                    PlayerStatDetail(
                        value = PlayerStatDetailValue(totalValue = 5),
                        type = PlayerStatDetailType(
                            name = "Assists",
                            developerName = "ASSISTS"
                        )
                    ),
                    PlayerStatDetail(
                        value = PlayerStatDetailValue(totalValue = 7),
                        type = PlayerStatDetailType(
                            name = "Yellowcards",
                            developerName = "YELLOWCARDS"
                        )
                    )
                )
            )
        )
    )


    val playerResponse = PlayerData(
        player = jamesTavernier
    )

    val playerModel = playerResponse.player.toPlayerModel()

    val playerStatistics = playerModel.statistics?.get(0)

        val teamSquadResponse = TeamSquadResponse(
            data = listOf(
                TeamSquadModelResponse(
                    id = 6517,
                    playerId = 758,
                    teamId = 62,
                    transferId = 18974,
                    jerseyNumber = 2,
                    captain = true,
                    player = jamesTavernier,
                    detailedPosition = DetailedPositionResponse(
                        id = 154,
                        name = "Right Back"
                    ),
                    position = PositionModelResponse(
                        id = 25,
                        name = "Defender"
                    )
                ),
                TeamSquadModelResponse(
                    id = 724310,
                    playerId = 28581,
                    teamId = 62,
                    transferId = 230582,
                    jerseyNumber = 9,
                    captain = false,
                    player = PlayerModelResponse(
                        id = 28581,
                        nationality = Nationality(
                            id = 716,
                            countryName = "Belgium",
                            flagImage = "https://cdn.sportmonks.com/images/soccer/teams/20/716.png"
                        ),
                        positionId = 27,
                        displayName = "Cyriel Dessers",
                        playerImage = "https://cdn.sportmonks.com/images/soccer/players/5/28581.png",
                        height = 185,
                        weight = 77,
                        gender = "male",
                        position = PositionModelResponse(
                            id = 27,
                            name = "Attacker"
                        ),
                        detailedPosition = DetailedPositionResponse(
                            id = 151,
                            name = "Centre Forward"
                        ),
                        dateOfBirth = "1994-12-08",
                        statistics = emptyList()
                    ),
                    detailedPosition = DetailedPositionResponse(
                        id = 151,
                        name = "Centre Forward"
                    ),
                    position = PositionModelResponse(
                        id = 27,
                        name = "Attacker"
                    )
                )
            )
        )

    val teamSquadModel = teamSquadResponse.data.map { it.toTeamSquadModel() }
    }

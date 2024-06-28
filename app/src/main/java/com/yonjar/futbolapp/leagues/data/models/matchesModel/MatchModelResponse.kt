package com.yonjar.futbolapp.leagues.data.models.matchesModel

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.data.models.teamsModel.TeamModelResponse
import com.yonjar.futbolapp.leagues.domain.models.EventModel
import com.yonjar.futbolapp.leagues.domain.models.MatchModel

data class MatchModelResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("starting_at") val startingAt: String?,
    @SerializedName("result_info") val result: String?,
    @SerializedName("participants") val teams: List<TeamModelResponse>?,
    @SerializedName("scores") val scores: List<ScoresResponseModel>?,
    @SerializedName("events") val events: List<EventModelResponse>?
) {
    fun toMatchModel(): MatchModel {

        val goalsListMinutes = mutableListOf<EventModel>()

        if (events?.isNotEmpty() == true) {
            for (n in events.indices) {
                if (events[n].addition?.contains("Goal") == true
                    && events[n].addition?.contains("Goal confirmed") == false
                    && events[n].addition?.contains("Goal Disallowed") == false
                    && events[n].addition?.contains("Goal Under Review") == false
                ) {
                    goalsListMinutes.add(events[n].toEventModel())
                }
            }
        }

        val teamsDiff = name?.split(" vs ")
        val homeTeam = teamsDiff?.get(0)
        val awayTeam = teamsDiff?.get(1)


        val homeGoals = scores?.let { getFinalScores(it).first }
        val awayGoals = scores?.let { getFinalScores(it).second }

        return MatchModel(
            id = id,
            name = name ?: "",
            date = startingAt ?: "",
            result = result,
            teamHome = if (homeTeam == teams?.get(0)?.name) teams?.get(0)
                ?.toTeamModel() else teams?.get(1)?.toTeamModel(),
            teamAway = if (awayTeam == teams?.get(1)?.name) teams?.get(1)
                ?.toTeamModel() else teams?.get(0)?.toTeamModel(),
            goalsHome = homeGoals,
            goalsAway = awayGoals,
            goalMinutes = goalsListMinutes.sortedBy { it.minute }
        )
    }
}

fun getFinalScores(scores: List<ScoresResponseModel>): Pair<Int, Int> {
    var homeGoals: Int? = null
    var awayGoals: Int? = null

    // Primero, ordenamos las entradas por descripción para asegurar que los valores de "CURRENT" y "2ND_HALF" sean considerados los últimos.
    val sortedScores = scores.sortedBy {
        when (it.description) {
            "CURRENT" -> 1
            "2ND_HALF" -> 2
            else -> 3
        }
    }

    for (score in sortedScores) {
        if (score.description == "CURRENT" || score.description == "2ND_HALF") {
            if (score.score?.participant == "home") {
                homeGoals = score.score.goals
            } else if (score.score?.participant == "away") {
                awayGoals = score.score.goals
            }
        }
    }

    // Por si acaso no encontramos "CURRENT" o "2ND_HALF", asignamos valores por defecto
    homeGoals =
        homeGoals ?: sortedScores.lastOrNull { it.score?.participant == "home" }?.score?.goals
    awayGoals =
        awayGoals ?: sortedScores.lastOrNull { it.score?.participant == "away" }?.score?.goals

    return Pair(homeGoals ?: 0, awayGoals ?: 0)
}


package com.yonjar.futbolapp.leagues.data.models.leagueModel

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.data.models.matchesModel.MatchModelResponse
import com.yonjar.futbolapp.leagues.domain.models.MatchModel
import com.yonjar.futbolapp.leagues.domain.models.leagueModels.LeagueModel

data class LeagueResponse(
    @SerializedName("data") val leagues: List<LeagueModelResponse>
)

data class OneLeagueResponse(
    @SerializedName("data") val league: LeagueModelResponse
)

data class LeagueModelResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("country_id") val countryId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image_path") val leagueImage: String,
    @SerializedName("sub_type") val subType:String,
    @SerializedName("currentseason") val currentSeason: CurrentSeasonModelResponse?,
    @SerializedName("upcoming") val upcomingMatches:List<MatchModelResponse>?,
    @SerializedName("latest") val latestMatches:List<MatchModelResponse>?
) {

    fun toLeagueModel(): LeagueModel {
        val matchesList:MutableList<MatchModel> = mutableListOf()
        if(upcomingMatches?.isNotEmpty() == true){
            for (n in upcomingMatches){
                matchesList.add(n.toMatchModel())
            }
        }

        val latestMatchesList:MutableList<MatchModel> = mutableListOf()
        if(latestMatches?.isNotEmpty() == true){
            for (n in latestMatches){
                latestMatchesList.add(n.toMatchModel())
            }
        }

        return LeagueModel(
            id = id, countryId = countryId,
            name = name,
            leagueImage = leagueImage,
            subType = subType,
            currentSeason = currentSeason?.toCurrentSeasonModel(),
            upcomingMatches = matchesList,
            latestMatches = latestMatchesList
        )
    }
}


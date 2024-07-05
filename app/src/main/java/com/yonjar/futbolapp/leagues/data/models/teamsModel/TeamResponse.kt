package com.yonjar.futbolapp.leagues.data.models.teamsModel

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.data.models.CountryResponse
import com.yonjar.futbolapp.leagues.data.models.VenueResponse
import com.yonjar.futbolapp.leagues.data.models.matchesModel.MatchModelResponse
import com.yonjar.futbolapp.leagues.domain.models.MatchModel
import com.yonjar.futbolapp.leagues.domain.models.teamModels.TeamModel

data class TeamResponse(
    @SerializedName("data") val data: TeamModelResponse
)

data class TeamModelResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("country_id") val countryId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("short_code") val shortName: String,
    @SerializedName("image_path") val teamImage: String,
    @SerializedName("founded") val yearFounded: Int,
    @SerializedName("country") val country: CountryResponse?,
    @SerializedName("venue") val venue: VenueResponse?,
    @SerializedName("activeseasons") val activeSeason: List<ActiveSeason>?,
    @SerializedName("upcoming") val upcomingMatches:List<MatchModelResponse>?,
    @SerializedName("latest") val latestMatches:List<MatchModelResponse>?
) {
    fun toTeamModel():TeamModel {

        val nextMatches:MutableList<MatchModel> = mutableListOf()
        if (upcomingMatches?.isNotEmpty() == true){
            for (n in upcomingMatches){
                nextMatches.add(n.toMatchModel())
            }
        }

        val latsMatchesList:MutableList<MatchModel> = mutableListOf()
        if(latestMatches?.isNotEmpty() == true){
            for (n in latestMatches){
                latsMatchesList.add(n.toMatchModel())
            }
        }

        return TeamModel(
            id = id,
            countryId = countryId,
            name = name,
            shortName = shortName,
            teamImage = teamImage,
            yearFounded = yearFounded,
            stadiumName = venue?.stadiumName,
            cityName = venue?.cityName,
            stadiumImage = venue?.stadiumImage,
            countryName = country?.countryName,
            countryFlag = country?.countryFlag,
            currentSeasonId = activeSeason?.get(0)?.seasonId,
            currentSeasonName = activeSeason?.get(0)?.name,
            nextMatches = nextMatches,
            lastMatches = latsMatchesList
        )
    }
}

data class ActiveSeason(
    @SerializedName("id") val seasonId: Int?,
    @SerializedName("name") val name: String?
)


package com.yonjar.futbolapp.leagues.data.models

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.domain.models.LeagueModel

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
    @SerializedName("image_path") val leagueImage: String
) {
    fun toLeagueModel(): LeagueModel =
        LeagueModel(id = id, countryId = countryId,
            name = name, leagueImage = leagueImage)
}


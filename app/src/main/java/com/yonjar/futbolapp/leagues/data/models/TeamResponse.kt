package com.yonjar.futbolapp.leagues.data.models

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.domain.models.TeamModel
import com.yonjar.futbolapp.leagues.domain.models.TeamSquadModel

data class TeamResponse(
    @SerializedName("data") val data:TeamModelResponse
)

data class TeamModelResponse(
    @SerializedName("id") val id:Int,
    @SerializedName("country_id") val countryId:Int,
    @SerializedName("name") val name:String,
    @SerializedName("short_code") val shortName:String,
    @SerializedName("image_path") val teamImage:String,
    @SerializedName("founded") val yearFounded:Int
){
    fun toTeamModel() = TeamModel(
        id = id,
        countryId = countryId,
        name = name,
        shortName = shortName,
        teamImage = teamImage,
        yearFounded = yearFounded
    )
}


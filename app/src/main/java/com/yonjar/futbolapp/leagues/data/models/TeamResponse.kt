package com.yonjar.futbolapp.leagues.data.models

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.domain.models.TeamModel

data class TeamResponse(
    @SerializedName("data") val data:TeamModelResponse
)

data class TeamModelResponse(
    @SerializedName("id") val id:Int,
    @SerializedName("country_id") val countryId:Int,
    @SerializedName("name") val name:String,
    @SerializedName("short_code") val shortName:String,
    @SerializedName("image_path") val teamImage:String,
    @SerializedName("founded") val yearFounded:Int,
    @SerializedName("country") val country:CountryResponse?,
    @SerializedName("venue") val venue:VenueResponse?
){
    fun toTeamModel() = TeamModel(
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
        countryFlag = country?.countryFlag

    )
}


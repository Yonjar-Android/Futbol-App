package com.yonjar.futbolapp.leagues.data.models.teamsModel

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.domain.models.PlayerModel
import com.yonjar.futbolapp.leagues.domain.models.PlayerStatistics

data class PlayerData(
    @SerializedName("data") val player: PlayerModelResponse
)
data class PlayerModelResponse(
    @SerializedName("id") val id:Int,
    @SerializedName("nationality") val nationality:Nationality?,
    @SerializedName("position_id") val positionId:Int?,
    @SerializedName("display_name") val displayName:String?,
    @SerializedName("image_path") val playerImage:String?,
    @SerializedName("height") val height:Int?,
    @SerializedName("weight") val weight:Int?,
    @SerializedName("gender") val gender:String?,
    @SerializedName("position") val position:PositionModelResponse?,
    @SerializedName("detailedposition") val detailedPosition:DetailedPositionResponse?,
    @SerializedName("date_of_birth") val dateOfBirth:String?,
    @SerializedName("statistics") val statistics:List<PlayerStatisticResponse>?
){
    fun toPlayerModel():PlayerModel {
        val stats = mutableListOf<PlayerStatistics>()

        for (n in statistics ?: mutableListOf()){
            stats.add(n.toPlayerStatistic())
        }

        return PlayerModel(
            playerId = id,
            transferId = null,
            captain = false,
            jerseyNumber = null,
            nationality = nationality?.countryName,
            positionId = positionId,
            name = displayName,
            playerImage = playerImage,
            height = height,
            weight = weight,
            gender = gender,
            position = position?.name,
            detailedPosition = detailedPosition?.name,
            statistics = stats,
            dateOfBirth = dateOfBirth,
            countryFlag = nationality?.flagImage
        )
    }
}

data class PositionModelResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name:String?
)

data class DetailedPositionResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name:String?
)

data class Nationality(
    @SerializedName("id") val id:Int?,
    @SerializedName("name") val countryName:String?,
    @SerializedName("image_path") val flagImage:String?
)
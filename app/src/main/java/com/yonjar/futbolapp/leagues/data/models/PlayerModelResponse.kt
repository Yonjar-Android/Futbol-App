package com.yonjar.futbolapp.leagues.data.models

import com.google.gson.annotations.SerializedName

data class PlayerModelResponse(
    @SerializedName("id") val id:Int,
    @SerializedName("nationality_id") val nationalityId:Int?,
    @SerializedName("position_id") val positionId:Int?,
    @SerializedName("display_name") val displayName:String?,
    @SerializedName("image_path") val playerImage:String?,
    @SerializedName("height") val height:Int?,
    @SerializedName("weight") val weight:Int?,
    @SerializedName("gender") val gender:String?
)

data class PositionModelResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name:String?
)

data class DetailedPositionResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name:String?
)

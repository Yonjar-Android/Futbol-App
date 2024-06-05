package com.yonjar.futbolapp.leagues.data.models.teamsModel.standingDetails

import com.google.gson.annotations.SerializedName

data class StandingDetails(
    @SerializedName("id") val id:Long,
    @SerializedName("value") val value:Int,
    @SerializedName("type") val type: StandingDetailsType
)

data class StandingDetailsType(
    @SerializedName("developer_name") val developerName:String
)

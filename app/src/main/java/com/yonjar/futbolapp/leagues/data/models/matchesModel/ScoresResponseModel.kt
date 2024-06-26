package com.yonjar.futbolapp.leagues.data.models.matchesModel

import com.google.gson.annotations.SerializedName

data class ScoresResponseModel(
    @SerializedName("id") val id:Int,
    @SerializedName("score") val score:ScoreData?
)

data class ScoreData(
    @SerializedName("goals") val goals:Int,
    @SerializedName("participant") val participant:String
)

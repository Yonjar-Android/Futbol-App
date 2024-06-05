package com.yonjar.futbolapp.leagues.data.models.leagueModel

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.domain.models.StageModel

class StageModelResponse(
    @SerializedName("id") val id:Int,
    @SerializedName("type_id") val typeId:Int,
    @SerializedName("name") val name:String
){
    fun toStageModel():StageModel = StageModel(
        stageId = id,
        typeId = typeId,
        nameStage = name
    )
}
package com.yonjar.futbolapp.leagues.data.models.leagueModel

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.domain.models.leagueModels.GroupModel

data class GroupResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
){
    fun toGroupModel(): GroupModel = GroupModel(
        id = id, name = name
    )
}
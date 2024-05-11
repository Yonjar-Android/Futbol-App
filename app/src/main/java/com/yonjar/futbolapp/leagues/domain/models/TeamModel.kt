package com.yonjar.futbolapp.leagues.domain.models


data class TeamModel(
    val id: Int,
    val countryId: Int,
    val name: String?,
    val shortName: String?,
    val teamImage: String?,
    val yearFounded: Int?
)
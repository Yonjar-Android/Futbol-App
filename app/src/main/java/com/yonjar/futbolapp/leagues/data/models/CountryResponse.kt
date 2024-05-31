package com.yonjar.futbolapp.leagues.data.models

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("id") val countryId:Int,
    @SerializedName("name") val countryName:String?,
    @SerializedName("image_path") val countryFlag:String?
)

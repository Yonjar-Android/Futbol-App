package com.yonjar.futbolapp.leagues.data.models

import com.google.gson.annotations.SerializedName

data class VenueResponse(
    @SerializedName("id") val venueId:Int,
    @SerializedName("name") val stadiumName:String?,
    @SerializedName("city_name") val cityName:String?,
    @SerializedName("image_path") val stadiumImage: String?
)

package com.yonjar.futbolapp.leagues.data.models.teamsModel

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.domain.models.PlayerStatisticDetail
import com.yonjar.futbolapp.leagues.domain.models.PlayerStatistics

data class PlayerStatisticResponse(
    @SerializedName("season_id") val seasonId:Int?,
    @SerializedName("details") val details: List<PlayerStatDetail>?
) {
    fun toPlayerStatistic(): PlayerStatistics {
        val statisticList = mutableListOf<PlayerStatisticDetail>()
        if (details?.isNotEmpty() == true) {
            for (n in details) {
                statisticList.add(n.toPlayerStatDetail())
            }
        }

        return PlayerStatistics(statisticList)

    }
}

data class PlayerStatDetail(
    @SerializedName("value") val value: PlayerStatDetailValue?,
    @SerializedName("type") val type: PlayerStatDetailType?
) {
    fun toPlayerStatDetail() = PlayerStatisticDetail(
        statisticTotal = value?.totalValue,
        statisticName = type?.name
    )
}

data class PlayerStatDetailValue(
    @SerializedName("total") val totalValue: Int?
)


data class PlayerStatDetailType(
    @SerializedName("name") val name: String?,
    @SerializedName("developer_name") val developerName: String?
)

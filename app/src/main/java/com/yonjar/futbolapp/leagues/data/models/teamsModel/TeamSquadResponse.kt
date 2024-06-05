package com.yonjar.futbolapp.leagues.data.models.teamsModel

import com.google.gson.annotations.SerializedName
import com.yonjar.futbolapp.leagues.domain.models.PlayerModel
import com.yonjar.futbolapp.leagues.domain.models.TeamSquadModel

data class TeamSquadResponse(
    @SerializedName("data") val data: List<TeamSquadModelResponse>
)

data class TeamSquadModelResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("player_id") val playerId: Int,
    @SerializedName("team_id") val teamId: Int?,
    @SerializedName("transfer_id") val transferId: Int?,
    @SerializedName("jersey_number") val jerseyNumber: Int?,
    @SerializedName("captain") val captain: Boolean?,
    @SerializedName("player") val player: PlayerModelResponse?,
    @SerializedName("detailedposition") val detailedPosition: DetailedPositionResponse?,
    @SerializedName("position") val position: PositionModelResponse?
) {
    fun toTeamSquadModel(): TeamSquadModel = TeamSquadModel(
        id = id,
        playerId = playerId,
        teamId = teamId,
        jerseyNumber = jerseyNumber,
        player = PlayerModel(
            playerId = playerId,
            transferId = transferId,
            captain = captain,
            jerseyNumber = jerseyNumber,
            nationalityId = player?.nationalityId,
            positionId = position?.id,
            name = player?.displayName,
            playerImage = player?.playerImage,
            height = player?.height,
            weight = player?.weight,
            gender = player?.gender,
            position = position?.name,
            detailedPosition = detailedPosition?.name
        )
    )
}
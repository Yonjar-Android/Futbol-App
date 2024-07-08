package com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.matchesInfoScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.yonjar.futbolapp.R

enum class MatchesTabs(
    val text:String,
    val petition:String
) {
    NextMatches(
        "NextMatches",
        petition = "upcoming.participants",

    ),
    LastsMatches(
        "LastMatches",
        petition = "latest.participants;latest.scores;latest.events"
    );
    @Composable
    fun getText(): String {
        return when (this) {
            NextMatches -> stringResource(id = R.string.nextMatches_str)
            LastsMatches -> stringResource(id = R.string.lastMatches_str)
        }
    }
}
package com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.matchesInfoScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.yonjar.futbolapp.R

enum class MatchesTabs(
    val text:String
) {
    NextMatches(
        "NextMatches"
    ),
    LastsMatches(
        "LastMatches"
    );
    @Composable
    fun getText(): String {
        return when (this) {
            NextMatches -> stringResource(id = R.string.lastMatches_str)
            LastsMatches -> stringResource(id = R.string.nextMatches_str)
        }
    }
}
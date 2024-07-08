package com.yonjar.futbolapp.leagues.ui.playerDetail.navPlayerScreen.navPlayerStats

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.yonjar.futbolapp.R
import com.yonjar.futbolapp.leagues.ui.common.ErrorFun
import com.yonjar.futbolapp.leagues.ui.common.LoadingFun
import com.yonjar.futbolapp.leagues.ui.playerDetail.PlayerState
import com.yonjar.futbolapp.leagues.ui.teamsDetail.navTeamsScreen.RowItem


@Composable
fun NavPlayerStatsScreen(
    state: PlayerState.Success,
    playerStatsViewModel: NavPlayerStatsViewModel,
    currentSeasonId: Int,
    navHostController: NavHostController
) {

    if(playerStatsViewModel.areStatsLoaded() != state.player.playerId){
        LaunchedEffect(state.player.playerId) {
            playerStatsViewModel.getPlayerStatistics(state.player.playerId, currentSeasonId)
        }
    }

    val stateC = playerStatsViewModel.state.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val currentState = stateC.value) {
            is NavPlayerStatsState.Error -> ErrorFun(currentState.errorMessage, context)
            NavPlayerStatsState.Loading -> LoadingFun()
            is NavPlayerStatsState.Success -> SuccessFun(currentState)
        }
    }

    BackHandler {
        navHostController.navigateUp()
    }
}

@Composable
fun SuccessFun(currentState: NavPlayerStatsState.Success) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(currentState.list.statisticList.isEmpty()){
            Text(text = stringResource(id = R.string.statsMessage_str), textAlign = TextAlign.Center)
        }
        for (n in 0 until currentState.list.statisticList.size step 2) {
            if (n + 1 < currentState.list.statisticList.size) {
                RowItem(
                    firstTitle = "${currentState.list.statisticList[n].statisticName}",
                    secondTitle = "${currentState.list.statisticList[n + 1].statisticName}",
                    value1 = "${currentState.list.statisticList[n].statisticTotal}",
                    value2 = "${currentState.list.statisticList[n + 1].statisticTotal}"
                )
            } else {
                RowItem(
                    firstTitle = "${currentState.list.statisticList[n].statisticName}",
                    secondTitle = "",
                    value1 = "${currentState.list.statisticList[n].statisticTotal}",
                    value2 = ""
                )
            }
        }
    }
}

package com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.yonjar.futbolapp.leagues.ui.leagueDetail.DetailLeagueState

@Composable
fun PlayOffInfoScreen(state: DetailLeagueState.Success, navController: NavController){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        LazyColumn {
            items(state.playOffStandings!!) { teamStanding ->
                StandingTeamItem(teamStanding, navController)
            }
        }
    }
}
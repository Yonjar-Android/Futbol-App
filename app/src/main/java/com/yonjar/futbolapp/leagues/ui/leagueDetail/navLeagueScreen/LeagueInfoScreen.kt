package com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.yonjar.futbolapp.leagues.domain.models.StandingModel
import com.yonjar.futbolapp.leagues.ui.leagueDetail.DetailLeagueState

@Composable
fun LeagueInfoScreen(state: DetailLeagueState.Success,navController:NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn {
            items(state.standings!!) { teamStanding ->
                StandingTeamItem(teamStanding, navController)
            }
        }
    }
}

@Composable
fun StandingTeamItem(teamStanding: StandingModel, navController: NavController) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            navController.navigate("TeamsScreen/${teamStanding.participantId}")
        }, verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = teamStanding.participant.teamImage,
            contentDescription = teamStanding.participant.name
        )
        Text(text = teamStanding.participant.name ?: "", fontSize = 15.sp)
        Text(
            text = teamStanding.points.toString(),
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
    }
}
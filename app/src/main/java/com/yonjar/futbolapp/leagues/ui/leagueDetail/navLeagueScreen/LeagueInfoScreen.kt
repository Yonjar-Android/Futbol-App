package com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.yonjar.futbolapp.R
import com.yonjar.futbolapp.leagues.domain.models.leagueModels.StandingModel
import com.yonjar.futbolapp.leagues.ui.leagueDetail.DetailLeagueState

@Composable
fun LeagueInfoScreen(state: DetailLeagueState.Success,navController:NavHostController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TableStanding()
        LazyColumn {
            items(state.standings!!) { teamStanding ->
                TeamRow(teamStanding, navController)
            }
        }
    }
}

@Composable
fun TeamRow(teamStanding: StandingModel, navController: NavHostController) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)
        .clickable {
            navController.navigate("TeamsScreen/${teamStanding.participantId}")
        },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model  = teamStanding.participant.teamImage,
            contentDescription = teamStanding.participant.name,
            modifier = Modifier
                .size(40.dp)
                .weight(1f)
        )
        Text(teamStanding.participant.name ?: "", modifier = Modifier.weight(3f))
        Text(teamStanding.details.wins.toString(), modifier = Modifier.weight(1f))
        Text(teamStanding.details.draws.toString(), modifier = Modifier.weight(1f))
        Text(teamStanding.details.losses.toString(), modifier = Modifier.weight(1f))
        Text(teamStanding.points.toString(), modifier = Modifier.weight(1f))
    }

    BackHandler {
        navController.navigateUp()
    }
}

@Composable
fun TableStanding(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Logo", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
        Text(stringResource(id = R.string.team_str), modifier = Modifier.weight(3f), fontWeight = FontWeight.Bold)
        Text("W", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
        Text("D", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
        Text("L", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
        Text("P", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
    }
}
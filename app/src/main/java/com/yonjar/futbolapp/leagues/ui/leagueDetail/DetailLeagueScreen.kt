package com.yonjar.futbolapp.leagues.ui.leagueDetail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.yonjar.futbolapp.leagues.domain.models.StandingModel

@Composable
fun DetailLeagueScreen(
    leagueId: Int,
    detailLeagueViewModel: DetailLeagueViewModel,
    navController: NavController
) {
    val state = detailLeagueViewModel.state.collectAsState()
    val context = LocalContext.current

    detailLeagueViewModel.chargeLeague(leagueId)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDEDEDE)),
        contentAlignment = Alignment.Center
    ) {

        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "arrowBack",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(15.dp)
                .size(30.dp)
                .clickable {
                    navController.popBackStack()
                })

        when (val currentState = state.value) {
            is DetailLeagueState.Error -> ErrorFun(currentState, context)
            DetailLeagueState.Loading -> LoadingFun()
            is DetailLeagueState.Success -> SuccessFun(currentState, navController)
        }

    }

}

@Composable
fun SuccessFun(state: DetailLeagueState.Success, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = state.league.leagueImage,
            contentDescription = state.league.name,
            modifier = Modifier
                .size(150.dp)
                .padding(horizontal = 5.dp)
        )

        LazyColumn {
            items(state.standings!!) { teamStanding ->
                StandingTeamItem(teamStanding, navController)
            }
        }
    }
}

@Composable
fun LoadingFun() {
        CircularProgressIndicator()
}

@Composable
fun ErrorFun(error: DetailLeagueState.Error, context: Context) {
    Toast.makeText(context, error.errorMessage, Toast.LENGTH_SHORT).show()
}

@Composable
fun StandingTeamItem(teamStanding: StandingModel, navController: NavController) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            navController.navigate("TeamsScreen/${teamStanding.participantId}")
        }, verticalAlignment = Alignment.CenterVertically) {

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

package com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yonjar.futbolapp.R
import com.yonjar.futbolapp.leagues.ui.leagueDetail.DetailLeagueState

@Composable
fun PlayOffInfoScreen(state: DetailLeagueState.Success, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Championship Section Header

        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if(state.playOffStandings!!.isNotEmpty()){
                        Text(
                            text = if (state.playOffStandings[0].stage?.nameStage == "Championship Round") {
                                "Championship Round"
                            } else {
                                "Championship Group"
                            },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        TableStanding()
                    }

                }
            }
        }
        if (state.playOffStandings!!.isNotEmpty()) {
            // Championship Section Items
            items(state.playOffStandings) { teamStanding ->
                if (teamStanding.group?.name == "Championship Group"
                    || teamStanding.stage?.nameStage == "Championship Round"
                ) {
                    TeamRow(teamStanding, navController)
                }
            }

            // Relegation Section Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (state.playOffStandings[0].stage?.nameStage == "Championship Round") {
                                "Relegation Round"
                            } else {
                                "Relegation Group"
                            },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        TableStanding()
                    }
                }
            }

            // Relegation Section Items
            items(state.playOffStandings) { teamStanding ->
                if (teamStanding.group?.name == "Relegation Group"
                    || teamStanding.stage?.nameStage == "Relegation Round"
                ) {
                    TeamRow(teamStanding, navController)
                }
            }
        } else {
            item {
                Text(text = stringResource(id = R.string.playOffMessage_str), textAlign = TextAlign.Center)
            }
        }
    }

    BackHandler {
        navController.navigateUp()
    }
}

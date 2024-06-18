package com.yonjar.futbolapp.leagues.ui.teamsDetail.navTeamsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.yonjar.futbolapp.leagues.domain.models.PlayerModel
import com.yonjar.futbolapp.leagues.ui.teamsDetail.TeamsScreenState

@Composable
fun TeamPlayersScreen(state: TeamsScreenState.Success, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text = "Plantilla", fontSize = 30.sp, modifier = Modifier.padding(vertical = 8.dp))

        LazyColumn{
            items(state.squadList) {
                PlayerItem(it.player, navController)
            }
        }
    }
}

@Composable
fun PlayerItem(player: PlayerModel, navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("PlayerScreen/${player.playerId}")
            }
    ) {
        AsyncImage(
            model = player.playerImage,
            contentDescription = player.name,
            modifier = Modifier.size(100.dp)
        )

        Text(text = player.name ?: "", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
    }
}
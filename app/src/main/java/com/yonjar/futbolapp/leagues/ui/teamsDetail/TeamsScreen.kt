package com.yonjar.futbolapp.leagues.ui.teamsDetail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.yonjar.futbolapp.leagues.domain.models.PlayerModel

@Composable
fun TeamsScreen(
    teamId: Int,
    navController: NavHostController,
    teamScreenViewModel: TeamScreenViewModel
) {
    teamScreenViewModel.loadTeam(teamId)

    val state = teamScreenViewModel.state.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Left) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "arrowBack",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        navController.popBackStack()
                    })
        }
        when (val currentState = state.value) {
            is TeamsScreenState.Error -> ErrorFun(currentState, context)
            TeamsScreenState.Loading -> LoadingFun()
            is TeamsScreenState.Success -> SuccessFun(currentState, navController)
        }
    }


}

@Composable
fun SuccessFun(state: TeamsScreenState.Success, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        AsyncImage(model = state.team.teamImage, contentDescription = state.team.name,
        modifier = Modifier.size(150.dp))
        Text(text = "Plantilla")
        LazyColumn() {
            items(state.squadList){
                PlayerItem(it.player,navController)
            }
        }
    }
}

@Composable
fun PlayerItem(player: PlayerModel, navController: NavHostController) {
     Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        AsyncImage(model = player.playerImage, contentDescription = player.name)
        Text(text = player.name ?: "", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun LoadingFun() {
        CircularProgressIndicator()
}

@Composable
fun ErrorFun(error: TeamsScreenState.Error, context: Context) {
    Toast.makeText(context, error.errorMessage, Toast.LENGTH_SHORT).show()
}

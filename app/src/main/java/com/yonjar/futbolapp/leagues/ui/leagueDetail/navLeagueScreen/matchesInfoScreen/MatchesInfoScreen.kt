package com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.matchesInfoScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.yonjar.futbolapp.leagues.domain.models.MatchModel
import com.yonjar.futbolapp.leagues.ui.leagueDetail.LoadingFun

@Composable
fun MatchesInfoScreen(
    matchesViewModel: MatchesViewModel,
    navController: NavController,
    leagueId: Int
) {
    val state = matchesViewModel.state.collectAsState()
    val context = LocalContext.current

    matchesViewModel.getMatches(leagueId)

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when(val currentState = state.value){
                    is MatchesState.Error -> ErrorFun(currentState,context)
                    MatchesState.Loading -> LoadingFun()
                    is MatchesState.Success -> SuccessFun(currentState)
                }
            }
        }
    )

}

@Composable
fun SuccessFun(currentState: MatchesState.Success) {
    LazyColumn {
        if(currentState.matchesState?.isNotEmpty() == true){
            items(currentState.matchesState){matchInfo ->
                MatchItem(matchInfo)
            }
        }
    }
}

@Composable
fun ErrorFun(currentState: MatchesState.Error, context: Context) {
    Toast.makeText(context, currentState.errorMessage, Toast.LENGTH_SHORT).show()
}

@Composable
fun MatchItem(matchInfo: MatchModel) {
    println(matchInfo.teamHome)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        AsyncImage(model = matchInfo.teamHome?.teamImage, contentDescription =  matchInfo.teamHome?.name)
        Text(text = "VS", fontSize = 48.sp, fontWeight = FontWeight.ExtraBold)
        AsyncImage(model = matchInfo.teamAway?.teamImage, contentDescription =  matchInfo.teamAway?.name)
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(text = "${matchInfo.name}", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
    }
}
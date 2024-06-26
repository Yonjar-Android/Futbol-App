package com.yonjar.futbolapp.leagues.ui.leagueDetail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.LeagueInfoScreen
import com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.PlayOffInfoScreen
import com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.matchesInfoScreen.MatchesInfoScreen
import com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.matchesInfoScreen.MatchesViewModel

@Composable
fun DetailLeagueScreen(
    leagueId: Int,
    detailLeagueViewModel: DetailLeagueViewModel,
    navController: NavController,
    matchesViewModel: MatchesViewModel
) {
    val state = detailLeagueViewModel.state.collectAsState()
    val context = LocalContext.current
    var rememberNumber by rememberSaveable {
        mutableStateOf(0)
    }

    if(leagueId != rememberNumber){
        LaunchedEffect(leagueId) {
            detailLeagueViewModel.chargeLeague(leagueId)
        }
        rememberNumber = leagueId
    }

    Scaffold(
        topBar = { MyTopLeagueBar(navController = navController) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (val currentState = state.value) {
                    is DetailLeagueState.Error -> ErrorFun(currentState, context)
                    DetailLeagueState.Loading -> LoadingFun()
                    is DetailLeagueState.Success -> SuccessFun(currentState, navController, matchesViewModel)
                }
            }
        }
    )
}


@Composable
fun SuccessFun(
    state: DetailLeagueState.Success,
    navController: NavController,
    matchesViewModel: MatchesViewModel
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = state.league.leagueImage,
            contentDescription = state.league.name,
            modifier = Modifier
                .size(120.dp)
                .padding(horizontal = 5.dp)
        )
        Text(
            text = "Temporada: ${state.league.currentSeason?.name}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp),
            color = Color.Black
        )
        MyLeagueScaffold(state = state, navController = navController, matchesViewModel= matchesViewModel)
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopLeagueBar(navController: NavController) {
    TopAppBar(title = { Text(text = "League") }, navigationIcon = {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
    })
}

@Composable
fun MyBottomTeamNavigation(navigationController: NavController) {

    var index by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        NavigationBarItem(selected = index == 0, onClick = {
            index = 0
            navigationController.navigate("LeagueInfoScreen")
        }, icon = {
            Icon(imageVector = Icons.Filled.Info, contentDescription = "Regular Season Screen")
        }, label = { Text(text = "Temporada") })


        NavigationBarItem(selected = index == 1, onClick = {
            index = 1
            navigationController.navigate("PlayOffInfoScreen")
        }, icon = {
            Icon(imageVector = Icons.Filled.Person, contentDescription = "Play-Offs Screen")
        }, label = { Text(text = "Play-Offs") })

        NavigationBarItem(selected = index == 2, onClick = {
            index = 2
            navigationController.navigate("MatchesInfoScreen")
        }, icon = {
            Icon(imageVector = Icons.Filled.Face, contentDescription = "Matches of the League")
        }, label = { Text(text = "Matches")})
    }
}

@Composable
fun MyLeagueScaffold(
    state: DetailLeagueState.Success,
    navController: NavController,
    matchesViewModel: MatchesViewModel
) {
    val navigationController = rememberNavController()
    Scaffold(
        content = {
            NavHost(
                navController = navigationController, startDestination = "LeagueInfoScreen",
                modifier = Modifier.padding(it)
            ) {
                composable(route = "LeagueInfoScreen") { LeagueInfoScreen(state, navController) }
                composable(route = "PlayOffInfoScreen") {
                    PlayOffInfoScreen(
                        state = state,
                        navController = navController
                    )
                }
                composable(route = "MatchesInfoScreen"){
                    MatchesInfoScreen(matchesViewModel, navController, state.league.id)
                }
            }

        }, bottomBar = {
            MyBottomTeamNavigation(navigationController)
        })
}

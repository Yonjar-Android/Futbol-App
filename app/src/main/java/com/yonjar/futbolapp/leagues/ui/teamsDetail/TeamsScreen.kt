package com.yonjar.futbolapp.leagues.ui.teamsDetail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yonjar.futbolapp.leagues.ui.teamsDetail.navTeamsScreen.InfoTeamScreen
import com.yonjar.futbolapp.leagues.ui.teamsDetail.navTeamsScreen.TeamPlayersScreen

@Composable
fun TeamsScreen(
    teamId: Int,
    navController: NavHostController,
    teamScreenViewModel: TeamScreenViewModel
) {

    val state = teamScreenViewModel.state.collectAsState()
    val context = LocalContext.current
    var rememberTeam by rememberSaveable {
        mutableIntStateOf(0)
    }

    if(teamId != rememberTeam){
        LaunchedEffect(teamId) {
            teamScreenViewModel.loadTeam(teamId)
        }
        rememberTeam = teamId
    }

    Scaffold(
        topBar = { MyTopTeamAppBar(navController) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (val currentState = state.value) {
                    is TeamsScreenState.Error -> ErrorFun(currentState, context)
                    TeamsScreenState.Loading -> LoadingFun()
                    is TeamsScreenState.Success -> SuccessFun(currentState, navController)
                }
            }
        }
    )
}

@Composable
fun SuccessFun(state: TeamsScreenState.Success, navController: NavHostController) {
    SimpleScaffold(state, navController)
}

@Composable
fun LoadingFun() {
    CircularProgressIndicator()
}

@Composable
fun ErrorFun(error: TeamsScreenState.Error, context: Context) {
    Toast.makeText(context, error.errorMessage, Toast.LENGTH_SHORT).show()
}

@Composable
fun MyBottomTeamNavigation(navigationController: NavHostController) {

    var index by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar() {
        NavigationBarItem(selected = index == 0, onClick = {
            index = 0
            navigationController.navigate("InfoTeamScreen")
        }, icon = {
            Icon(imageVector = Icons.Filled.Info, contentDescription = "Team Info")
        }, label = { Text(text = "Team Info") })


        NavigationBarItem(selected = index == 1, onClick = {
            index = 1
            navigationController.navigate("TeamPlayersScreen")
        }, icon = {
            Icon(imageVector = Icons.Filled.Person, contentDescription = "Players")
        }, label = { Text(text = "Players") })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopTeamAppBar(navController: NavHostController) {
    TopAppBar(title = { Text(text = "Team Information") }, navigationIcon = {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
    })
}

@Composable
fun SimpleScaffold(state: TeamsScreenState.Success, navController: NavHostController) {
    val navigationController = rememberNavController()
    Scaffold(
        content = {
            NavHost(
                navController = navigationController, startDestination = "InfoTeamScreen",
                modifier = Modifier.padding(it)
            ) {
                composable(route = "InfoTeamScreen") { InfoTeamScreen(state, navController) }
                composable(route = "TeamPlayersScreen") { TeamPlayersScreen(state, navController) }
            }

        }, bottomBar = { MyBottomTeamNavigation(navigationController) })
}


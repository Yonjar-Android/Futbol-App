package com.yonjar.futbolapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yonjar.futbolapp.leagues.ui.leagueDetail.DetailLeagueScreen
import com.yonjar.futbolapp.leagues.ui.leagueDetail.DetailLeagueViewModel
import com.yonjar.futbolapp.leagues.ui.leagueMainScreen.LeaguesScreen
import com.yonjar.futbolapp.leagues.ui.leagueMainScreen.LeaguesViewModel
import com.yonjar.futbolapp.leagues.ui.teamsDetail.TeamScreenViewModel
import com.yonjar.futbolapp.leagues.ui.teamsDetail.TeamsScreen
import com.yonjar.futbolapp.ui.theme.FutbolAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val leaguesViewModel: LeaguesViewModel by viewModels()

    private val detailLeagueViewModel: DetailLeagueViewModel by viewModels()

    private val teamScreenViewModel:TeamScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FutbolAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "LeaguesScreen") {
                        composable(route = "LeaguesScreen") {
                            LeaguesScreen(leaguesViewModel, navController)
                        }
                        composable(route = "DetailLeagueScreen/{leagueId}", arguments = listOf(
                            navArgument(name = "leagueId") {
                                type = NavType.IntType
                            }
                        )) { args ->
                            args.arguments?.getInt("leagueId")
                                ?.let { DetailLeagueScreen(leagueId = it, detailLeagueViewModel, navController) }
                        }

                        composable(route = "TeamsScreen/{teamId}", arguments = listOf(
                            navArgument(name = "teamId"){
                                type = NavType.IntType
                            }
                        )){args ->
                            args.arguments?.getInt("teamId")
                                ?.let { TeamsScreen(teamId = it, navController, teamScreenViewModel) }
                        }
                    }
                }
            }
        }
    }
}

package com.yonjar.futbolapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.matchesInfoScreen.MatchesViewModel
import com.yonjar.futbolapp.leagues.ui.leagueMainScreen.LeaguesScreen
import com.yonjar.futbolapp.leagues.ui.leagueMainScreen.LeaguesViewModel
import com.yonjar.futbolapp.leagues.ui.playerDetail.PlayerScreen
import com.yonjar.futbolapp.leagues.ui.playerDetail.PlayerScreenViewModel
import com.yonjar.futbolapp.leagues.ui.playerDetail.navPlayerScreen.navPlayerStats.NavPlayerStatsViewModel
import com.yonjar.futbolapp.leagues.ui.teamsDetail.TeamScreenViewModel
import com.yonjar.futbolapp.leagues.ui.teamsDetail.TeamsScreen
import com.yonjar.futbolapp.ui.theme.FutbolAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val leaguesViewModel: LeaguesViewModel by viewModels()

    private val detailLeagueViewModel: DetailLeagueViewModel by viewModels()

    private val teamScreenViewModel:TeamScreenViewModel by viewModels()

    private val playerScreenViewModel:PlayerScreenViewModel by viewModels()

    private val playerStatsViewModel:NavPlayerStatsViewModel by viewModels()

    private val matchesViewModel:MatchesViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
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
                                ?.let { DetailLeagueScreen(leagueId = it, detailLeagueViewModel, navController, matchesViewModel = matchesViewModel) }
                        }

                        composable(route = "TeamsScreen/{teamId}", arguments = listOf(
                            navArgument(name = "teamId"){
                                type = NavType.IntType
                            }
                        )){args ->
                            args.arguments?.getInt("teamId")
                                ?.let { TeamsScreen(teamId = it, navController, teamScreenViewModel) }
                        }
                        composable(
                            route = "PlayerScreen/{playerId}/{currentSeasonId}",
                            arguments = listOf(
                                navArgument(name = "playerId") {
                                    type = NavType.IntType
                                },
                                navArgument(name = "currentSeasonId") {
                                    type = NavType.IntType
                                }
                            )
                        ) { args ->
                            val playerId = args.arguments?.getInt("playerId")
                            val currentSeasonId = args.arguments?.getInt("currentSeasonId")

                            if (playerId != null && currentSeasonId != null) {
                                PlayerScreen(
                                    playerId = playerId,
                                    currentSeasonId = currentSeasonId,
                                    navHostController = navController,
                                    playerScreenViewModel = playerScreenViewModel,
                                    playerStatsViewModel = playerStatsViewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

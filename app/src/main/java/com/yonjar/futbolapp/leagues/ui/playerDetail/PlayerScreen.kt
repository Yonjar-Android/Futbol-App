package com.yonjar.futbolapp.leagues.ui.playerDetail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yonjar.futbolapp.R
import com.yonjar.futbolapp.leagues.ui.common.ErrorFun
import com.yonjar.futbolapp.leagues.ui.common.LoadingFun
import com.yonjar.futbolapp.leagues.ui.playerDetail.navPlayerScreen.NavPlayerInfoScreen
import com.yonjar.futbolapp.leagues.ui.playerDetail.navPlayerScreen.navPlayerStats.NavPlayerStatsScreen
import com.yonjar.futbolapp.leagues.ui.playerDetail.navPlayerScreen.navPlayerStats.NavPlayerStatsViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlayerScreen(
    playerId: Int,
    currentSeasonId: Int,
    navHostController: NavHostController,
    playerScreenViewModel: PlayerScreenViewModel,
    playerStatsViewModel: NavPlayerStatsViewModel
) {
    val state = playerScreenViewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(playerId) {
        playerScreenViewModel.getPlayerInformation(playerId)
    }

    Scaffold(
        topBar = { MyTopPlayerAppBar(navHostController) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (val currentState = state.value) {
                    is PlayerState.Error -> ErrorFun(currentState.errorMessage, context)
                    PlayerState.Loading -> LoadingFun()
                    is PlayerState.Success -> SuccessFun(
                        currentState,
                        playerStatsViewModel,
                        currentSeasonId,
                        navHostController
                    )
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SuccessFun(
    currentState: PlayerState.Success,
    playerStatsViewModel: NavPlayerStatsViewModel,
    currentSeasonId: Int,
    navHostController: NavHostController
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(currentState.player.playerImage)
                .crossfade(true)
                .error(R.drawable.placeholder)
                .build(),
            contentDescription = currentState.player.name,
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = currentState.player.name ?: "",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 3.dp)
        )
        HorizontalDivider(modifier = Modifier.padding(2.dp))

        MyPlayerScaffold(
            currentState = currentState,
            playerStatsViewModel = playerStatsViewModel,
            currentSeasonId = currentSeasonId,
            navHostController = navHostController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopPlayerAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.playerInfo_str)) },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        })
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyPlayerScaffold(
    currentState: PlayerState.Success,
    playerStatsViewModel: NavPlayerStatsViewModel,
    currentSeasonId: Int,
    navHostController: NavHostController
) {
    val navigationController = rememberNavController()

    Scaffold(
        content = {
            NavHost(
                navController = navigationController, startDestination = "NavPlayerInfoScreen",
                modifier = Modifier.padding(it)
            ) {
                composable(route = "NavPlayerInfoScreen") {
                    NavPlayerInfoScreen(currentState, navHostController)
                }
                composable(route = "NavPlayerStatsScreen") {
                    NavPlayerStatsScreen(
                        state = currentState,
                        playerStatsViewModel = playerStatsViewModel,
                        currentSeasonId = currentSeasonId,
                        navHostController = navHostController
                    )
                }
            }

        }, bottomBar = {
            MyBottomPlayerNavigation(navigationController)
        })
}

@Composable
fun MyBottomPlayerNavigation(navigationController: NavHostController) {

    var index by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar() {
        NavigationBarItem(selected = index == 0, onClick = {
            index = 0
            navigationController.navigate("NavPlayerInfoScreen")
        }, icon = {
            Icon(
                painter = painterResource(id = R.drawable.circle_info_solid), contentDescription = "Player Information",
                modifier = Modifier.size(25.dp)
            )
        }, label = { Text(text = stringResource(id = R.string.data_str)) })


        NavigationBarItem(selected = index == 1, onClick = {
            index = 1
            navigationController.navigate("NavPlayerStatsScreen")
        }, icon = {
            Icon(painter = painterResource(id = R.drawable.chart_simple_solid), contentDescription = "Player Statistics",
                modifier = Modifier.size(25.dp))
        }, label = { Text(text = "Stats") })
    }
}
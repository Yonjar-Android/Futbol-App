package com.yonjar.futbolapp.leagues.ui.playerDetail

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
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
                    is PlayerState.Error -> ErrorFun(currentState, context)
                    PlayerState.Loading -> LoadingFun()
                    is PlayerState.Success -> SuccessFun(
                        currentState,
                        navHostController,
                        playerStatsViewModel,
                        currentSeasonId
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
    navController: NavHostController,
    playerStatsViewModel: NavPlayerStatsViewModel,
    currentSeasonId:Int
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
            currentSeasonId = currentSeasonId
        )
    }
}

@Composable
fun LoadingFun() {
    CircularProgressIndicator()
}

@Composable
fun ErrorFun(currentState: PlayerState.Error, context: Context) {
    Toast.makeText(context, currentState.errorMessage, Toast.LENGTH_SHORT).show()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopPlayerAppBar(navController: NavHostController) {
    TopAppBar(title = { Text(text = "Player Information") }, navigationIcon = {
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
    currentSeasonId: Int
) {
    val navigationController = rememberNavController()

    Scaffold(
        content = {
            NavHost(
                navController = navigationController, startDestination = "NavPlayerInfoScreen",
                modifier = Modifier.padding(it)
            ) {
                composable(route = "NavPlayerInfoScreen") {
                    NavPlayerInfoScreen(currentState)
                }
                composable(route = "NavPlayerStatsScreen") {
                    NavPlayerStatsScreen(
                        state = currentState,
                        playerStatsViewModel = playerStatsViewModel,
                        currentSeasonId = currentSeasonId
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
            Icon(imageVector = Icons.Filled.Info, contentDescription = "Player Information")
        }, label = { Text(text = "Info") })


        NavigationBarItem(selected = index == 1, onClick = {
            index = 1
            navigationController.navigate("NavPlayerStatsScreen")
        }, icon = {
            Icon(imageVector = Icons.Filled.Person, contentDescription = "Player Statistics")
        }, label = { Text(text = "Stats") })
    }
}
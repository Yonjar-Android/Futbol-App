package com.yonjar.futbolapp.leagues.ui.leagueMainScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.yonjar.futbolapp.leagues.data.models.LeagueModel

@Composable

fun LeaguesScreen(leaguesViewModel: LeaguesViewModel, navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "European Leagues", fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
        MyListTest(leaguesViewModel, navController)
    }
}

@Composable
fun MyListTest(leaguesViewModel: LeaguesViewModel, navController: NavController) {
    val state = leaguesViewModel.state.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val currentState = state.value) {
            is LeaguesState.Loading -> {
                CircularProgressIndicator()
            }
            is LeaguesState.Success -> {
                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    items(currentState.leagues!!) { league ->
                        ItemView(league, navController)
                    }
                }
            }
            is LeaguesState.Error -> {
                Toast.makeText(context, currentState.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Log.i("message", state.value.toString())
}

@Composable
fun ItemView(league: LeagueModel, navController: NavController) {
    Card(modifier= Modifier
        .fillMaxWidth()
        .padding(vertical = 5.dp)
        .clickable {
            navController.navigate("DetailLeagueScreen/${league.id}")
        }) {
        Column(modifier= Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = league.name,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Monospace,
                fontSize = 25.sp,
                modifier = Modifier.padding(10.dp),
                fontWeight = FontWeight.SemiBold
            )

            AsyncImage(model = league.leagueImage, contentDescription = league.name,
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.LightGray))
        }

    }
}

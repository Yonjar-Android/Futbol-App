package com.yonjar.futbolapp.leagues.ui.leagueDetail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun DetailLeagueScreen(leagueId: Int, detailLeagueViewModel: DetailLeagueViewModel){
    val state = detailLeagueViewModel.state.collectAsState()
    val context = LocalContext.current

    detailLeagueViewModel.chargeLeague(leagueId)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        when(val currentState = state.value){
            is DetailLeagueState.Error -> ErrorFun(currentState, context)
            DetailLeagueState.Loading -> LoadingFun()
            is DetailLeagueState.Success -> SuccessFun(currentState)
        }

    }

}

@Composable
fun SuccessFun(state: DetailLeagueState.Success) {
    Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = state.league.name, fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
        AsyncImage(model = state.league.leagueImage, contentDescription = state.league.name)
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

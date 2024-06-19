package com.yonjar.futbolapp.leagues.ui.teamsDetail.navTeamsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.yonjar.futbolapp.leagues.ui.teamsDetail.TeamsScreenState

@Composable
fun InfoTeamScreen(state: TeamsScreenState.Success) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        AsyncImage(
            model = state.team.teamImage, contentDescription = state.team.name,
            modifier = Modifier
                .size(200.dp)
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )

        RowItem(
            firstTitle = "Nombre del equipo",
            secondTitle = "Año de fundación",
            value1 = state.team.name ?: "",
            value2 = state.team.yearFounded.toString()
        )

        RowItem(
            firstTitle = "Country",
            secondTitle = "City",
            value1 = state.team.countryName ?: "",
            value2 = state.team.cityName ?: ""
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Text(text = "Estadio", fontWeight = FontWeight.Bold)
                Text(text = state.team.stadiumName ?: "")
                
                if(state.team.stadiumImage != null){
                    AsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        model = state.team.stadiumImage,
                        contentDescription = state.team.stadiumName ?: ""
                    )
                } else{
                    Text(text = "No image")
                }
                
            
            }
        }

    }
}

@Composable
fun RowItem(firstTitle: String, secondTitle: String, value1: String, value2: String) {

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
            Text(text = firstTitle, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = value1, fontSize = 17.sp)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
            Text(text = secondTitle, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = value2, fontSize = 17.sp)
        }
    }
}



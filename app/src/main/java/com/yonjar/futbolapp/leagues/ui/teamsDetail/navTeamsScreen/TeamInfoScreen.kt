package com.yonjar.futbolapp.leagues.ui.teamsDetail.navTeamsScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.yonjar.futbolapp.R
import com.yonjar.futbolapp.leagues.ui.teamsDetail.TeamsScreenState

@Composable
fun InfoTeamScreen(state: TeamsScreenState.Success, navHostController: NavHostController) {
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
            firstTitle = stringResource(id = R.string.teamName_str),
            secondTitle = stringResource(id = R.string.foundation_str),
            value1 = state.team.name ?: "",
            value2 = state.team.yearFounded.toString()
        )

        RowItem(
            firstTitle = stringResource(id = R.string.country_str),
            secondTitle = stringResource(id = R.string.city_str),
            value1 = state.team.countryName ?: "",
            value2 = state.team.cityName ?: ""
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(id = R.string.stadium_str), fontWeight = FontWeight.Bold)
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

    BackHandler {
        navHostController.navigateUp()
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



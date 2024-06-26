package com.yonjar.futbolapp.leagues.ui.playerDetail.navPlayerScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yonjar.futbolapp.leagues.ui.playerDetail.PlayerState
import com.yonjar.futbolapp.leagues.ui.teamsDetail.navTeamsScreen.RowItem
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavPlayerInfoScreen(
    currentState: PlayerState.Success,
) {
    println(currentState.player.playerId)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp, top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        RowItem(
            firstTitle = "Edad",
            secondTitle = "Fecha de nacimiento",
            value1 = calcularEdad(currentState.player.dateOfBirth ?: "").toString(),
            value2 = currentState.player.dateOfBirth ?: ""
        )

        RowItem(
            firstTitle = "Estatura",
            secondTitle = "Peso",
            value1 = currentState.player.height.toString(),
            value2 = currentState.player.weight.toString()
        )

        RowItem(
            firstTitle = "Posición",
            secondTitle = "Posición específica",
            value1 = currentState.player.position ?: "Undefined",
            value2 = currentState.player.detailedPosition ?: "Undefined"
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Nacionalidad", fontWeight = FontWeight.Bold)
                Text(text = currentState.player.nationality ?: "")
                AsyncImage(
                    model = currentState.player.countryFlag,
                    contentDescription = currentState.player.nationality
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calcularEdad(fechaNacimiento: String): Int {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val fechaNacimientoParsed = LocalDate.parse(fechaNacimiento, formatter)
    val fechaActual = LocalDate.now()
    return Period.between(fechaNacimientoParsed, fechaActual).years
}

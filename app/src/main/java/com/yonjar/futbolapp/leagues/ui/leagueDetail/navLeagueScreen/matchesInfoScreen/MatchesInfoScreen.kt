package com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.matchesInfoScreen

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.yonjar.futbolapp.leagues.domain.models.MatchModel
import com.yonjar.futbolapp.leagues.ui.common.ErrorFun
import com.yonjar.futbolapp.leagues.ui.common.LoadingFun
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MatchesInfoScreen(
    matchesViewModel: MatchesViewModel,
    leagueId: Int,
    navHostController: NavHostController
) {
    val state = matchesViewModel.state.collectAsState()
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = { MatchesTabs.entries.size }
    )
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    LaunchedEffect(pagerState.currentPage) {
        val matchType = MatchesTabs.entries[pagerState.currentPage]
        matchesViewModel.getMatches(leagueId, matchType)
    }

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex.value,
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    MatchesTabs.entries.forEachIndexed { index, matchesTabs ->
                        Tab(selected = selectedTabIndex.value == index, onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(matchesTabs.ordinal)
                            }
                        },
                            text = {
                                Text(text = matchesTabs.getText())
                            }
                        )
                    }
                }

                when (val currentState = state.value) {
                    is MatchesState.Error -> ErrorFun(
                        error = currentState.errorMessage,
                        context = context
                    )
                    MatchesState.Loading -> LoadingFun()
                    is MatchesState.Success -> SuccessFun(currentState, pagerState)
                }
            }
        }
    )
    BackHandler {
        navHostController.navigateUp()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuccessFun(
    currentState: MatchesState.Success,
    pagerState: PagerState
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn {
            if (currentState.matchesState?.isNotEmpty() == true) {
                items(currentState.matchesState) { matchInfo ->
                    MatchItem(matchInfo)
                }
            } else {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "There's no more games this season")
                    }
                }
            }
        }
    }

}

@Composable
fun MatchItem(matchInfo: MatchModel) {
    Column(modifier = Modifier.padding(vertical = 20.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = matchInfo.name, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
        }

        if (matchInfo.result?.isNotBlank() == true) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = matchInfo.date,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = matchInfo.teamHome?.teamImage,
                contentDescription = matchInfo.teamHome?.name,
                modifier = Modifier.size(100.dp)
            )
            Text(text = "VS", fontSize = 36.sp, fontWeight = FontWeight.ExtraBold)
            AsyncImage(
                model = matchInfo.teamAway?.teamImage,
                contentDescription = matchInfo.teamAway?.name,
                modifier = Modifier.size(100.dp)
            )
        }
        if (matchInfo.result?.isNotBlank() == true) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = matchInfo.goalsHome.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = "-",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = matchInfo.goalsAway.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                ) {
                    if (matchInfo.goalMinutes?.isNotEmpty() == true) {
                        Column(modifier = Modifier.weight(1f)) {
                            for (n in matchInfo.goalMinutes) {
                                if (matchInfo.teamHome?.id == n.participant) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "${n.player} ${n.minute.toString()}",
                                            textAlign = TextAlign.Center,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            for (n in matchInfo.goalMinutes) {
                                if (matchInfo.teamAway?.id == n.participant) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "${n.player} ${n.minute.toString()}",
                                            textAlign = TextAlign.Center,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = matchInfo.date,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }

    }


}
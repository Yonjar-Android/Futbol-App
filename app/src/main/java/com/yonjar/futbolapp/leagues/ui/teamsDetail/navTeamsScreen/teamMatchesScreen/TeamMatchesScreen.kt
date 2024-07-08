@file:OptIn(ExperimentalFoundationApi::class)

package com.yonjar.futbolapp.leagues.ui.teamsDetail.navTeamsScreen.teamMatchesScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.navigation.NavHostController
import com.yonjar.futbolapp.leagues.ui.common.ErrorFun
import com.yonjar.futbolapp.leagues.ui.common.LoadingFun
import com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.matchesInfoScreen.MatchItem
import com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.matchesInfoScreen.MatchesTabs
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TeamMatchesScreen(
    teamId: Int,
    navHostController: NavHostController,
    teamMatchesViewModel: TeamMatchesViewModel
) {

    val context = LocalContext.current
    val state = teamMatchesViewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = { MatchesTabs.entries.size }
    )
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }

    LaunchedEffect(pagerState.currentPage) {
        val matchType = MatchesTabs.entries[pagerState.currentPage]
        teamMatchesViewModel.getMatchesByTeamId(teamId, matchType)
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
                    is TeamMatchesState.Error -> ErrorFun(currentState.errorMessage, context)
                    TeamMatchesState.Loading -> LoadingFun()
                    is TeamMatchesState.Success -> SuccessFun(currentState, pagerState)
                }
            }
        }
    )

    BackHandler {
        navHostController.navigateUp()
    }
}

@Composable
fun SuccessFun(currentState: TeamMatchesState.Success, pagerState: PagerState) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn {
            if (currentState.matches.isNotEmpty()) {
                items(currentState.matches) { match ->
                    MatchItem(matchInfo = match)
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

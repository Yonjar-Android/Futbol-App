package com.yonjar.futbolapp.leagues.ui.teamsDetail.navTeamsScreen.teamMatchesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonjar.futbolapp.leagues.data.repositories.RepositoryTeams
import com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.matchesInfoScreen.MatchesTabs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamMatchesViewModel @Inject constructor(private val repositoryTeams: RepositoryTeams) :
    ViewModel() {
    private val _state = MutableStateFlow<TeamMatchesState>(TeamMatchesState.Loading)
    var state: StateFlow<TeamMatchesState> = _state

    fun getMatchesByTeamId(teamId: Int, matchType: MatchesTabs) {
        viewModelScope.launch {
            try {
                _state.value = TeamMatchesState.Loading
                val response = when (matchType) {
                    MatchesTabs.NextMatches -> repositoryTeams.getMatchesByTeamId(teamId,matchType.petition)
                    MatchesTabs.LastsMatches -> repositoryTeams.getMatchesByTeamId(teamId,matchType.petition)
                }

                if(response != null){
                    _state.value = TeamMatchesState.Success(response)
                } else{
                    _state.value = TeamMatchesState.Error("Response was null")
                }

            } catch (e:Exception){
                _state.value = TeamMatchesState.Error("Error: ${e.message}")
            }
        }
    }
}
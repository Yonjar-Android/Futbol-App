package com.yonjar.futbolapp.leagues.ui.teamsDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonjar.futbolapp.leagues.data.repositories.RepositoryTeams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamScreenViewModel @Inject constructor(private val repositoryTeams: RepositoryTeams): ViewModel() {
    private val _state = MutableStateFlow<TeamsScreenState>(TeamsScreenState.Loading)
    var state: StateFlow<TeamsScreenState> = _state

    fun loadTeam(teamId:Int){
        viewModelScope.launch {
            try {
                val responseTeam = repositoryTeams.getTeamById(teamId)
                val responseSquad = repositoryTeams.getTeamSquadById(teamId)

                if(responseTeam != null && responseSquad != null){
                    _state.value = TeamsScreenState.Success(responseTeam, responseSquad)
                } else{
                    _state.value = TeamsScreenState.Error("Response was null")
                }

            } catch (e:Exception){
                _state.value = TeamsScreenState.Error("Error: ${e.message}")
            }
        }
    }
}
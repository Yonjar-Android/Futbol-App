package com.yonjar.futbolapp.leagues.ui.playerDetail.navPlayerScreen.navPlayerStats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonjar.futbolapp.leagues.data.repositories.RepositoryTeams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavPlayerStatsViewModel @Inject
constructor(private val repositoryTeams: RepositoryTeams)
    : ViewModel() {
    private val _state = MutableStateFlow<NavPlayerStatsState>(NavPlayerStatsState.Loading)
    var state: StateFlow<NavPlayerStatsState> = _state
    fun getPlayerStatistics(playerId: Int, currentSeasonId:Int) {
        viewModelScope.launch {
            try {
                val response = repositoryTeams.getPlayerStatistics(playerId,currentSeasonId)
                if(response != null){
                    _state.value = NavPlayerStatsState.Success(response)
                } else{
                    _state.value = NavPlayerStatsState.Error("Response was null")
                }
            } catch (e:Exception){
                _state.value = NavPlayerStatsState.Error(e.message ?: "")
            }
        }
    }

}
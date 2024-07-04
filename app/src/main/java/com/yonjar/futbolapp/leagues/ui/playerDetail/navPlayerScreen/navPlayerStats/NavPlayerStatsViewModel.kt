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

    private var actualPlayer:Int = 0

    fun getPlayerStatistics(playerId: Int, currentSeasonId: Int) {
        if (actualPlayer == playerId) return
        viewModelScope.launch {
            _state.value = NavPlayerStatsState.Loading
            try {
                val response = repositoryTeams.getPlayerStatistics(playerId, currentSeasonId)
                if (response != null) {
                    _state.value = NavPlayerStatsState.Success(response)
                    actualPlayer = playerId
                } else {
                    _state.value = NavPlayerStatsState.Error("Response was null")
                }
            } catch (e: Exception) {
                _state.value = NavPlayerStatsState.Error("Error: ${e.message}")
            }
        }
    }
    fun areStatsLoaded(): Int {
        return actualPlayer
    }
}
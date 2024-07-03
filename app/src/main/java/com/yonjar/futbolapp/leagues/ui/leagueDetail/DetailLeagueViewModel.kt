package com.yonjar.futbolapp.leagues.ui.leagueDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonjar.futbolapp.leagues.data.repositories.RepositoryLeagues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailLeagueViewModel @Inject constructor
    (private val repositoryLeagues: RepositoryLeagues)
    :ViewModel() {
    private val _state = MutableStateFlow<DetailLeagueState>(DetailLeagueState.Loading)
    var state:StateFlow<DetailLeagueState> = _state

    fun chargeLeague(leagueId:Int){
        viewModelScope.launch {
            _state.value = DetailLeagueState.Loading
            try {
                val response = repositoryLeagues.getLeagueById(leagueId)
                val response2 = repositoryLeagues.getStandingBySeasonId(response?.currentSeason?.idSeason)
                val responsePlayOff = repositoryLeagues.getStandingPlayOffOneBySeasonId(response?.currentSeason?.idSeason)

                if(response != null && response2 != null && responsePlayOff != null){
                    _state.value = DetailLeagueState.Success(response,response2,responsePlayOff)
                } else{
                    _state.value = DetailLeagueState.Error("Response was null")
                }
            } catch (e: Exception) {
                _state.value = DetailLeagueState.Error("Error: ${e.message}")
                println("Error: ${e.message}")
            }
        }
    }
}
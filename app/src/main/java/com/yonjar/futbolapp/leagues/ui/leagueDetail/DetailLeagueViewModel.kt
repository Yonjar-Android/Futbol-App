package com.yonjar.futbolapp.leagues.ui.leagueDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonjar.futbolapp.leagues.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailLeagueViewModel @Inject constructor
    (private val repository: Repository)
    :ViewModel() {
    private val _state = MutableStateFlow<DetailLeagueState>(DetailLeagueState.Loading)
    var state:StateFlow<DetailLeagueState> = _state

    fun chargeLeague(leagueId:Int){
        viewModelScope.launch {
            try {
                val response = repository.getLeagueById(leagueId)
                val response2 = repository.getStandingBySeasonId(0)

                if(response != null && response2 != null){
                    _state.value = DetailLeagueState.Success(response,response2)
                } else{
                    _state.value = DetailLeagueState.Error("Response was null")
                }
            } catch (e: Exception) {
                _state.value = DetailLeagueState.Error("Error: ${e.message}")
            }
        }
    }
}
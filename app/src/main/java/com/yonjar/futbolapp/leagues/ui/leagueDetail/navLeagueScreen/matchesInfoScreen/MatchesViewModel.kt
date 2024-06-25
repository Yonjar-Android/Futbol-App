package com.yonjar.futbolapp.leagues.ui.leagueDetail.navLeagueScreen.matchesInfoScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonjar.futbolapp.leagues.data.repositories.RepositoryLeagues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(private val repositoryLeagues: RepositoryLeagues):ViewModel() {
    private val _state = MutableStateFlow<MatchesState>(MatchesState.Loading)
    var state: StateFlow<MatchesState> = _state

    fun getMatches(id:Int, type:MatchesTabs){
        viewModelScope.launch {
            try {
                _state.value = MatchesState.Loading
                val response = when(type){
                    MatchesTabs.NextMatches -> repositoryLeagues.getMatchesByLeagueId(id,"upcoming.participants")
                    MatchesTabs.LastsMatches -> repositoryLeagues.getMatchesByLeagueId(id,"latest.participants")
                }

                if(response != null){
                    _state.value = MatchesState.Success(response)
                } else{
                    _state.value = MatchesState.Error("Response was null")
                }
            } catch (e:Exception){
             _state.value = MatchesState.Error("Error: ${e.message}")
            }
        }
    }
}
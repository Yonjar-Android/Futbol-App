package com.yonjar.futbolapp.leagues.ui.leagueMainScreen

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
class LeaguesViewModel @Inject constructor(private val repositoryLeagues: RepositoryLeagues):ViewModel() {
    private val _state = MutableStateFlow<LeaguesState>(LeaguesState.Loading)
    var state:StateFlow<LeaguesState> = _state

    init {
        viewModelScope.launch {
           val list = repositoryLeagues.getLeagues()
            Log.i("MessagePlayers", list.toString())
            if(list.isNullOrEmpty()){
                _state.value = LeaguesState.Error("Error cargando")
            } else {
                _state.value = LeaguesState.Success(list)
            }
        }
    }
}
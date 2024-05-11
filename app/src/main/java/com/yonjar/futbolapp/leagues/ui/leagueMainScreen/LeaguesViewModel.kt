package com.yonjar.futbolapp.leagues.ui.leagueMainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonjar.futbolapp.leagues.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaguesViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    private val _state = MutableStateFlow<LeaguesState>(LeaguesState.Loading)
    var state:StateFlow<LeaguesState> = _state

    init {
        viewModelScope.launch {
           val list = repository.getLeagues()
            Log.i("MessagePlayers", list.toString())
            if(list.isNullOrEmpty()){
                _state.value = LeaguesState.Error("Error cargando")
            } else {
                _state.value = LeaguesState.Success(list)
            }
        }
    }
}
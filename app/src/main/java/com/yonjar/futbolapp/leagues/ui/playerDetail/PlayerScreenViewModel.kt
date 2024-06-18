package com.yonjar.futbolapp.leagues.ui.playerDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yonjar.futbolapp.leagues.data.repositories.RepositoryTeams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerScreenViewModel @Inject constructor(private val repositoryTeams: RepositoryTeams) :
    ViewModel() {

    private val _state = MutableStateFlow<PlayerState>(PlayerState.Loading)
    var state: StateFlow<PlayerState> = _state

    fun getPlayerInformation(playerId: Int) {
        viewModelScope.launch {
            try {
                val response = repositoryTeams.getPlayerById(playerId)
                if(response != null){
                    _state.value = PlayerState.Success(response)
                } else{
                    _state.value = PlayerState.Error("Response was null")
                }
            } catch (e:Exception){
                _state.value = PlayerState.Error("${e.message}")
            }
        }
    }

    fun getPlayerStatistics(playerId: Int) {

    }

}
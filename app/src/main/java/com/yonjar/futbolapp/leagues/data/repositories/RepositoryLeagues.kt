package com.yonjar.futbolapp.leagues.data.repositories

import com.yonjar.futbolapp.leagues.data.network.LeagueService
import com.yonjar.futbolapp.leagues.domain.models.MatchModel
import com.yonjar.futbolapp.leagues.domain.models.leagueModels.LeagueModel
import com.yonjar.futbolapp.leagues.domain.models.leagueModels.StandingModel
import com.yonjar.futbolapp.leagues.domain.repositories.RepositoryLeagues
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryLeagues @Inject constructor(private val leagueService: LeagueService):
    RepositoryLeagues {

    override suspend fun getLeagues():List<LeagueModel>?{
        runCatching {
            leagueService.getLeagues()
        }.onSuccess {
            val leaguesList = mutableListOf<LeagueModel>()

            for (n in it.leagues){
                if(n.subType == "domestic"){
                    leaguesList.add(n.toLeagueModel())
                }
            }
            return leaguesList
        }
            .onFailure { println( "Error: $it") }
        return null
    }

    override suspend fun getLeagueById(id:Int): LeagueModel?{
        runCatching {
            leagueService.getLeagueById(id)
        }.onSuccess {
            return it.league.toLeagueModel() }
            .onFailure { println("Error: ${it.message}") }
        return null
    }

    override suspend fun getStandingBySeasonId(id:Int?):List<StandingModel>?{
        runCatching {
            leagueService.getStandingsBySeasonId(id)
        }.onSuccess { standings ->
            val newList = mutableListOf<StandingModel>()
            for (s in standings.data){
                if(s.stage?.name == "Regular Season" || s.stage?.name == "1st Phase"){
                    newList.add(s.toStandingModel())
                }
            }
            return newList
        }
        .onFailure { println("Error: ${it.message}") }
        return null
    }

    override suspend fun getStandingPlayOffOneBySeasonId(id: Int?): List<StandingModel>? {
        runCatching {
            leagueService.getStandingsBySeasonId(id)
        }.onSuccess { standings ->
            val newList = mutableListOf<StandingModel>()

            for (s in standings.data){
                when{
                    s.stage?.name ==  "Championship Round" || s.stage?.name == "Relegation Round" -> {newList.add(s.toStandingModel())}

                    s.group?.name == "Championship Group" || s.group?.name == "Relegation Group" -> {
                        newList.add(s.toStandingModel())}
                }
            }
            return newList
        }.onFailure {
            println("Error: ${it.message}")
        }
        return null
    }

    override suspend fun getMatchesByLeagueId(id: Int?, include:String): List<MatchModel>? {
        runCatching {
            leagueService.getMatchesByLeagueId(id,include)
        }.onSuccess {

            if(include == "upcoming.participants"){
                return it.league.toLeagueModel().upcomingMatches
            }

            else if(include == "latest.participants;latest.scores;latest.events"){
                return it.league.toLeagueModel().latestMatches
            }
            return null

        }.onFailure {
            println("Error: ${it.message}")
        }
        return null
    }


}
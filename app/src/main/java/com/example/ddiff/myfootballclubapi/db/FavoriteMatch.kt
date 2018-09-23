package com.example.ddiff.myfootballclubapi.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
/**
 * Created by Diffa Dwi Desyawan on 21/9/18.
 * email : diffadwi1@gmail.com.
 */
@Parcelize
data class FavoriteMatch(
        val id: Long? = null,
        val idEvent: String? = "",
        val strHomeTeam: String? = "",
        val strAwayTeam: String? = "",
        val intHomeScore: String? = "",
        val intAwayScore: String? = "",
        val strDate: String? = "",
        val strHomeLineupGoalkeeper: String? = "",
        val strAwayLineupGoalkeeper: String? = "",
        val strHomeGoalDetails: String? = "",
        val strAwayGoalDetails: String? = "",
        val intHomeShots: String? = "",
        val intAwayShots: String? = "",
        val strHomeLineupDefense: String? = "",
        val strHomeLineupMidfield: String? = "",
        val strAwayLineupDefense: String? = "",
        val strAwayLineupMidfield: String? = "",
        val strHomeLineupForward: String? = "",
        val strAwayLineupForward: String? = "",
        val strHomeLineupSubstitutes: String? = "",
        val strAwayLineupSubstitutes: String? = "",
        val strTeamBadge: String? = "",
        val idHomeTeam: String? = "",
        val idAwayTeam: String? = "",
        val strAwayYellowCards: String? = "",
        val strHomeYellowCards: String? = "",
        val strAwayRedCards: String? = "",
        val strHomeRedCards: String? = ""
) : Parcelable {
    companion object {
        const val TABLE_MATCH_FAVORITE: String = "TABLE_MATCH_FAVORITE"
        const val ID: String = "ID_"
        const val ID_EVENT: String = "ID_EVENT"
        const val HOME_TEAM_STR: String = "HOME_TEAM_STR"
        const val AWAY_TEAM_STR: String = "STR_AWAY_TEAM_STR"
        const val INT_HOME_SCORE: String = "INT_HOME_SCORE"
        const val INT_AWAY_SCORE: String = "INT_AWAY_SCORE"
        const val STR_DATE: String = "STR_DATE"
        const val HOME_LINEUP_GOALKEEPER_STR: String = "HOME_LINEUP_GOALKEEPER_STR"
        const val AWAY_LINEUP_GOALKEEPER_STR: String = "AWAY_LINEUP_GOALKEEPER_STR"
        const val HOME_GOAL_DETAILS_STR: String = "HOME_GOAL_DETAILS_STR"
        const val AWAY_GOAL_DETAILS_STR: String = "AWAY_GOAL_DETAILS_STR"
        const val INT_HOME_SHOTS: String = "INT_HOME_SHOTS"
        const val INT_AWAY_SHOTS: String = "INT_AWAY_SHOTS"
        const val HOME_LINEUP_DEFENSE_STR: String = "HOME_LINEUP_DEFENSE_STR"
        const val HOME_LINEUP_MIDFIELD_STR: String = "AWAY_DEFENSE"
        const val AWAY_LINEUP_DEFENSE_STR: String = "AWAY_LINEUP_DEFENSE_STR"
        const val AWAY_LINEUP_MIDFIELD_STR: String = "AWAY_LINEUP_MIDFIELD_STR"
        const val HOME_LINEUP_FORWARD_STR: String = "HOME_LINEUP_FORWARD_STR"
        const val AWAY_LINEUP_FORWARD_STR: String = "AWAY_LINEUP_FORWARD_STR"
        const val HOME_LINEUP_SUBSTITUTES_STR: String = "HOME_LINEUP_SUBSTITUTES_STR"
        const val AWAY_LINEUP_SUBSTITUTES_STR: String = "AWAY_LINEUP_SUBSTITUTES_STR"
        const val TEAM_BADGE_STR: String = "TEAM_BADGE_STR"
        const val ID_HOME_TEAM: String = "ID_HOME_TEAM"
        const val ID_AWAY_TEAM: String = "ID_AWAY_TEAM"
        const val AWAY_YELLOW_CARDS: String = "AWAY_YELLOW_CARDS"
        const val AWAY_RED_CARDS: String = "AWAY_RED_CARDS"
        const val HOME_YELLOW_CARDS: String = "HOME_YELLOW_CARDS"
        const val HOME_RED_CARDS: String = "HOME_RED_CARDS"
    }
}
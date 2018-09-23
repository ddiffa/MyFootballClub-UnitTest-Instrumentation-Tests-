package com.example.ddiff.myfootballclubapi.mvp.ui.activity

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.R.drawable.ic_add_to_favorites
import com.example.ddiff.myfootballclubapi.R.drawable.ic_added_to_favorites
import com.example.ddiff.myfootballclubapi.R.id.add_to_favorite
import com.example.ddiff.myfootballclubapi.R.menu.detail_menu
import com.example.ddiff.myfootballclubapi.db.FavoriteMatch
import com.example.ddiff.myfootballclubapi.db.database
import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
import com.example.ddiff.myfootballclubapi.mvp.model.TeamModel
import com.example.ddiff.myfootballclubapi.mvp.presenter.detail.MatchDetailContract
import com.example.ddiff.myfootballclubapi.mvp.presenter.detail.MatchDetailPresenter
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.AppSchedulerProvider
import com.example.ddiff.myfootballclubapi.utils.getKeyActivity
import com.example.ddiff.myfootballclubapi.utils.getKeyIntent
import com.example.ddiff.myfootballclubapi.utils.getSimpleDate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_statistic_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * Created by Diffa Dwi Desyawan on 21/9/18.
 * email : diffadwi1@gmail.com.
 */
class DetailActivity : AppCompatActivity(), MatchDetailContract.View {

    private lateinit var matchModel: MatchModel
    private lateinit var teamModel: TeamModel
    private lateinit var favoriteMatch: FavoriteMatch
    private lateinit var presenter: MatchDetailPresenter

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var idEvent: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val request = ApiService()
        val scheduler = AppSchedulerProvider()
        if (intent.getStringExtra(getKeyActivity()) == "match") {
            matchModel = intent.getParcelableExtra(getKeyIntent())
            idEvent = matchModel.idEvent.toString()
            supportActionBar?.title = matchModel.strHomeTeam + " vs " + matchModel.strAwayTeam
            showMatch()
            presenter = MatchDetailPresenter(this, request, scheduler)
            presenter.getTeams(matchModel.idHomeTeam, matchModel.idAwayTeam)
            favoriteState()
        } else if (intent.getStringExtra(getKeyActivity()) == "favorite") {
            favoriteMatch = intent.getParcelableExtra(getKeyIntent())
            idEvent = favoriteMatch.idEvent.toString()
            supportActionBar?.title = favoriteMatch.strHomeTeam + " vs " + favoriteMatch.strAwayTeam
            showMatchFavorite()
            presenter = MatchDetailPresenter(this, request, scheduler)
            presenter.getTeams(favoriteMatch.idHomeTeam, favoriteMatch.idAwayTeam)
            favoriteState()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                try {
                    if (isFavorite) removeFromFavorite() else if (intent.getStringExtra(getKeyActivity()) == "match") {
                        addToFavorite()
                    } else {
                        addToMyFavorite()
                    }
                    isFavorite = !isFavorite
                    setFavorite()
                } catch (e: Exception) {
                    Snackbar.make(findViewById(R.id.li_detail), "Pertandingan belum tersimpan, silakan coba lagi", Snackbar.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if (isFavorite) menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavoriteMatch.TABLE_MATCH_FAVORITE,
                        FavoriteMatch.ID_EVENT to matchModel.idEvent,
                        FavoriteMatch.HOME_TEAM_STR to matchModel.strHomeTeam,
                        FavoriteMatch.AWAY_TEAM_STR to matchModel.strAwayTeam,
                        FavoriteMatch.INT_HOME_SCORE to matchModel.intHomeScore,
                        FavoriteMatch.INT_AWAY_SCORE to matchModel.intAwayScore,
                        FavoriteMatch.STR_DATE to matchModel.strDate,
                        FavoriteMatch.HOME_LINEUP_GOALKEEPER_STR to matchModel.strHomeLineupGoalkeeper,
                        FavoriteMatch.AWAY_LINEUP_GOALKEEPER_STR to matchModel.strAwayLineupGoalkeeper,
                        FavoriteMatch.HOME_GOAL_DETAILS_STR to matchModel.strHomeGoalDetails,
                        FavoriteMatch.AWAY_GOAL_DETAILS_STR to matchModel.strAwayGoalDetails,
                        FavoriteMatch.INT_HOME_SHOTS to matchModel.intHomeShots,
                        FavoriteMatch.INT_AWAY_SHOTS to matchModel.intAwayShots,
                        FavoriteMatch.HOME_LINEUP_DEFENSE_STR to matchModel.strHomeLineupDefense,
                        FavoriteMatch.HOME_LINEUP_MIDFIELD_STR to matchModel.strHomeLineupMidfield,
                        FavoriteMatch.AWAY_LINEUP_DEFENSE_STR to matchModel.strAwayLineupDefense,
                        FavoriteMatch.AWAY_LINEUP_MIDFIELD_STR to matchModel.strAwayLineupMidfield,
                        FavoriteMatch.HOME_LINEUP_FORWARD_STR to matchModel.strHomeLineupForward,
                        FavoriteMatch.AWAY_LINEUP_FORWARD_STR to matchModel.strAwayLineupForward,
                        FavoriteMatch.HOME_LINEUP_SUBSTITUTES_STR to matchModel.strHomeLineupSubstitutes,
                        FavoriteMatch.AWAY_LINEUP_SUBSTITUTES_STR to matchModel.strAwayLineupSubstitutes,
                        FavoriteMatch.TEAM_BADGE_STR to teamModel.strTeamBadge,
                        FavoriteMatch.ID_HOME_TEAM to matchModel.idHomeTeam,
                        FavoriteMatch.ID_AWAY_TEAM to matchModel.idAwayTeam,
                        FavoriteMatch.AWAY_YELLOW_CARDS to matchModel.strAwayYellowCards,
                        FavoriteMatch.AWAY_RED_CARDS to matchModel.strAwayRedCards,
                        FavoriteMatch.HOME_YELLOW_CARDS to matchModel.strHomeYellowCards,
                        FavoriteMatch.HOME_RED_CARDS to matchModel.strHomeRedCards)
            }
            Snackbar.make(findViewById(R.id.li_detail), "Pertandingan telah ditambahkan ke favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(findViewById(R.id.li_detail), "Pertandingan gagal ditambahkan ke favorite", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun addToMyFavorite() {
        try {
            database.use {
                insert(FavoriteMatch.TABLE_MATCH_FAVORITE,
                        FavoriteMatch.ID_EVENT to favoriteMatch.idEvent,
                        FavoriteMatch.HOME_TEAM_STR to favoriteMatch.strHomeTeam,
                        FavoriteMatch.AWAY_TEAM_STR to favoriteMatch.strAwayTeam,
                        FavoriteMatch.INT_HOME_SCORE to favoriteMatch.intHomeScore,
                        FavoriteMatch.INT_AWAY_SCORE to favoriteMatch.intAwayScore,
                        FavoriteMatch.STR_DATE to favoriteMatch.strDate,
                        FavoriteMatch.HOME_LINEUP_GOALKEEPER_STR to favoriteMatch.strHomeLineupGoalkeeper,
                        FavoriteMatch.AWAY_LINEUP_GOALKEEPER_STR to favoriteMatch.strAwayLineupGoalkeeper,
                        FavoriteMatch.HOME_GOAL_DETAILS_STR to favoriteMatch.strHomeGoalDetails,
                        FavoriteMatch.AWAY_GOAL_DETAILS_STR to favoriteMatch.strAwayGoalDetails,
                        FavoriteMatch.INT_HOME_SHOTS to favoriteMatch.intHomeShots,
                        FavoriteMatch.INT_AWAY_SHOTS to favoriteMatch.intAwayShots,
                        FavoriteMatch.HOME_LINEUP_DEFENSE_STR to favoriteMatch.strHomeLineupDefense,
                        FavoriteMatch.HOME_LINEUP_MIDFIELD_STR to favoriteMatch.strHomeLineupMidfield,
                        FavoriteMatch.AWAY_LINEUP_DEFENSE_STR to favoriteMatch.strAwayLineupDefense,
                        FavoriteMatch.AWAY_LINEUP_MIDFIELD_STR to favoriteMatch.strAwayLineupMidfield,
                        FavoriteMatch.HOME_LINEUP_FORWARD_STR to favoriteMatch.strHomeLineupForward,
                        FavoriteMatch.AWAY_LINEUP_FORWARD_STR to favoriteMatch.strAwayLineupForward,
                        FavoriteMatch.HOME_LINEUP_SUBSTITUTES_STR to favoriteMatch.strHomeLineupSubstitutes,
                        FavoriteMatch.AWAY_LINEUP_SUBSTITUTES_STR to favoriteMatch.strAwayLineupSubstitutes,
                        FavoriteMatch.TEAM_BADGE_STR to favoriteMatch.strTeamBadge,
                        FavoriteMatch.ID_HOME_TEAM to favoriteMatch.idHomeTeam,
                        FavoriteMatch.ID_AWAY_TEAM to favoriteMatch.idAwayTeam,
                        FavoriteMatch.AWAY_YELLOW_CARDS to favoriteMatch.strAwayYellowCards,
                        FavoriteMatch.AWAY_RED_CARDS to favoriteMatch.strAwayRedCards,
                        FavoriteMatch.HOME_YELLOW_CARDS to favoriteMatch.strHomeYellowCards,
                        FavoriteMatch.HOME_RED_CARDS to favoriteMatch.strHomeRedCards)
            }
            Snackbar.make(findViewById(R.id.li_detail), "Pertandingan telah ditambahkan ke favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(findViewById(R.id.li_detail), "Pertandingan gagal ditambahkan ke favorite", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteMatch.TABLE_MATCH_FAVORITE,
                        "(ID_EVENT = {idEvent})",
                        "idEvent" to idEvent)
            }
            Snackbar.make(findViewById(R.id.li_detail),
                    "Pertandingan telah dihapus dari favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(findViewById(R.id.li_detail),
                    "Pertandingan gagal dihapus dari favorite", Snackbar.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun showMatch() {
        tv_club_name_away_detail.text = matchModel.strAwayTeam
        tv_club_name_home_detail.text = matchModel.strHomeTeam

        tv_score_away_club_detail.text = matchModel.intAwayScore
        tv_score_home_club_detail.text = matchModel.intHomeScore

        tv_date_detail.text = getSimpleDate(matchModel.strDate)

        tv_goal_home.text = matchModel.strHomeGoalDetails
        tv_goal_away.text = matchModel.strAwayGoalDetails

        tv_shot_home.text = matchModel.intHomeShots
        tv_shot_away.text = matchModel.intAwayShots

        tv_gk_home.text = matchModel.strHomeLineupGoalkeeper
        tv_gk_away.text = matchModel.strAwayLineupGoalkeeper

        tv_defense_home.text = matchModel.strHomeLineupDefense
        tv_defense_away.text = matchModel.strAwayLineupDefense

        tv_midfield_home.text = matchModel.strHomeLineupMidfield
        tv_midfield_away.text = matchModel.strAwayLineupMidfield

        tv_foward_home.text = matchModel.strHomeLineupForward
        tv_foward_away.text = matchModel.strAwayLineupForward

        tv_sub_home.text = matchModel.strHomeLineupSubstitutes
        tv_sub_away.text = matchModel.strAwayLineupSubstitutes

        tv_yellow_home.text = matchModel.strHomeYellowCards
        tv_yellow_away.text = matchModel.strAwayYellowCards

        tv_red_home.text = matchModel.strHomeRedCards
        tv_red_away.text = matchModel.strAwayRedCards

    }

    @SuppressLint("SimpleDateFormat")
    private fun showMatchFavorite() {
        tv_club_name_away_detail.text = favoriteMatch.strAwayTeam
        tv_club_name_home_detail.text = favoriteMatch.strHomeTeam

        tv_score_away_club_detail.text = favoriteMatch.intAwayScore
        tv_score_home_club_detail.text = favoriteMatch.intHomeScore

        tv_date_detail.text = getSimpleDate(favoriteMatch.strDate)

        tv_goal_home.text = favoriteMatch.strHomeGoalDetails
        tv_goal_away.text = favoriteMatch.strAwayGoalDetails

        tv_shot_home.text = favoriteMatch.intHomeShots
        tv_shot_away.text = favoriteMatch.intAwayShots

        tv_gk_home.text = favoriteMatch.strHomeLineupGoalkeeper
        tv_gk_away.text = favoriteMatch.strAwayLineupGoalkeeper

        tv_defense_home.text = favoriteMatch.strHomeLineupDefense
        tv_defense_away.text = favoriteMatch.strAwayLineupDefense

        tv_midfield_home.text = favoriteMatch.strHomeLineupMidfield
        tv_midfield_away.text = favoriteMatch.strAwayLineupMidfield

        tv_foward_home.text = favoriteMatch.strHomeLineupForward
        tv_foward_away.text = favoriteMatch.strAwayLineupForward

        tv_sub_home.text = favoriteMatch.strHomeLineupSubstitutes
        tv_sub_away.text = favoriteMatch.strAwayLineupSubstitutes

        tv_yellow_home.text = favoriteMatch.strHomeYellowCards
        tv_yellow_away.text = favoriteMatch.strAwayYellowCards

        tv_red_home.text = favoriteMatch.strHomeRedCards
        tv_red_away.text = favoriteMatch.strAwayRedCards
    }

    override fun showLogo1(data: List<TeamModel>) {
        Picasso.get().load(data[0].strTeamBadge).into(img_home_club_detail)
        teamModel = data[0]
    }

    override fun showLogo2(data: List<TeamModel>) {
        Picasso.get().load(data[0].strTeamBadge).into(img_away_club_detail)
        teamModel = data[0]
    }

    override fun showLoadErrorMessage(throwable: Throwable?) {
        Toast.makeText(applicationContext, "Failed to display list. \n ${throwable?.localizedMessage}", Toast.LENGTH_LONG).show()
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteMatch.TABLE_MATCH_FAVORITE)
                    .whereArgs("(ID_EVENT = {idEvent})", "idEvent" to idEvent)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}

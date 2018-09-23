package com.example.ddiff.myfootballclubapi.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
/**
 * Created by Diffa Dwi Desyawan on 21/9/18.
 * email : diffadwi1@gmail.com.
 */
class MyDBOpenHelper (context: Context) : ManagedSQLiteOpenHelper(context,
        "myFootball.db",
        null,
        1) {

    companion object {
        private var instance : MyDBOpenHelper?=null

        fun getInstance (context: Context) : MyDBOpenHelper{
            if (instance == null){
                instance = MyDBOpenHelper(context.applicationContext)
            }
            return instance as MyDBOpenHelper
        }
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(FavoriteMatch.TABLE_MATCH_FAVORITE, true,
                FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteMatch.ID_EVENT to TEXT + UNIQUE,
                FavoriteMatch.HOME_TEAM_STR to TEXT,
                FavoriteMatch.AWAY_TEAM_STR to TEXT,
                FavoriteMatch.INT_HOME_SCORE to TEXT,
                FavoriteMatch.INT_AWAY_SCORE to TEXT,
                FavoriteMatch.STR_DATE to TEXT,
                FavoriteMatch.HOME_LINEUP_GOALKEEPER_STR to TEXT,
                FavoriteMatch.AWAY_LINEUP_GOALKEEPER_STR to TEXT,
                FavoriteMatch.HOME_GOAL_DETAILS_STR to TEXT,
                FavoriteMatch.AWAY_GOAL_DETAILS_STR to TEXT,
                FavoriteMatch.INT_HOME_SHOTS to TEXT,
                FavoriteMatch.INT_AWAY_SHOTS to TEXT,
                FavoriteMatch.HOME_LINEUP_DEFENSE_STR to TEXT,
                FavoriteMatch.HOME_LINEUP_MIDFIELD_STR to TEXT,
                FavoriteMatch.AWAY_LINEUP_DEFENSE_STR to TEXT,
                FavoriteMatch.AWAY_LINEUP_MIDFIELD_STR to TEXT,
                FavoriteMatch.HOME_LINEUP_FORWARD_STR to TEXT,
                FavoriteMatch.AWAY_LINEUP_FORWARD_STR to TEXT,
                FavoriteMatch.HOME_LINEUP_SUBSTITUTES_STR to TEXT,
                FavoriteMatch.AWAY_LINEUP_SUBSTITUTES_STR to TEXT,
                FavoriteMatch.TEAM_BADGE_STR to TEXT,
                FavoriteMatch.ID_HOME_TEAM to TEXT,
                FavoriteMatch.ID_AWAY_TEAM to TEXT,
                FavoriteMatch.AWAY_YELLOW_CARDS to TEXT,
                FavoriteMatch.AWAY_RED_CARDS to TEXT,
                FavoriteMatch.HOME_YELLOW_CARDS to TEXT,
                FavoriteMatch.HOME_RED_CARDS to TEXT)    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.dropTable(FavoriteMatch.TABLE_MATCH_FAVORITE, true)
    }
}


val Context.database: MyDBOpenHelper
    get() = MyDBOpenHelper.getInstance(applicationContext)
package com.example.ddiff.myfootballclubapi.mvp.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.R.id.*
import com.example.ddiff.myfootballclubapi.mvp.ui.fragment.FavoriteFragment
import com.example.ddiff.myfootballclubapi.mvp.ui.fragment.MatchFragment
import com.example.ddiff.myfootballclubapi.mvp.ui.fragment.NextMatchFragment
import com.example.ddiff.myfootballclubapi.utils.ActivityUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    /**
     * Created by Diffa Dwi Desyawan on 15/9/18.
     * email : diffadwi1@gmail.com.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bot_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                menu_match -> {
                    supportActionBar?.title = resources.getString(R.string.match)
                    ActivityUtil().loadFragment(MatchFragment(), supportFragmentManager)
                    item.isChecked = true
                }
                menu_next_match -> {
                    supportActionBar?.title = resources.getString(R.string.next_match)
                    ActivityUtil().loadFragment(NextMatchFragment(), supportFragmentManager)
                    item.isChecked = true
                }
                menu_fav -> {
                    supportActionBar?.title = resources.getString(R.string.favorite)
                    ActivityUtil().loadFragment(FavoriteFragment(), supportFragmentManager)
                    item.isChecked = true
                }
            }
            true
        }
        bot_navigation.selectedItemId = menu_match
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}


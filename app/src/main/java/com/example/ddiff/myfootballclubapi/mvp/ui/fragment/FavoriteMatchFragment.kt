package com.example.ddiff.myfootballclubapi.mvp.ui.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.adapter.FavoriteMatchAdapter
import com.example.ddiff.myfootballclubapi.db.FavoriteMatch
import com.example.ddiff.myfootballclubapi.db.database
import com.example.ddiff.myfootballclubapi.mvp.ui.activity.DetailActivity
import com.example.ddiff.myfootballclubapi.utils.getKeyActivity
import com.example.ddiff.myfootballclubapi.utils.getKeyIntent
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * Created by Diffa Dwi Desyawan on 21/9/18.
 * email : diffadwi1@gmail.com.
 */
class FavoriteFragment : Fragment(), AnkoComponent<Context>, FavoriteMatchAdapter.OnItemClickListener {


    private lateinit var rvMatch: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var favoriteMatch: MutableList<FavoriteMatch> = mutableListOf()

    private lateinit var adapter: FavoriteMatchAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteMatchAdapter(favoriteMatch, this)

        rvMatch.adapter = adapter
        showFavorite()

        swipeRefresh.onRefresh {
            favoriteMatch.clear()
            showFavorite()
        }
    }

    private fun showFavorite() {
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteMatch.TABLE_MATCH_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favoriteMatch.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            orientation = LinearLayout.VERTICAL
            padding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorPrimary)
                id = R.id.swipeFav
                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)
                    rvMatch = recyclerView {
                        id = R.id.rv_match_favorite
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }
            }
        }
    }

    override fun itemDetail(model: FavoriteMatch) {
        startActivity<DetailActivity>(getKeyIntent() to model, getKeyActivity() to "favorite")
    }
}
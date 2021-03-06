package com.example.ddiff.myfootballclubapi.mvp.ui.fragment


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.adapter.MatchAdapter
import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
import com.example.ddiff.myfootballclubapi.mvp.presenter.nextmatch.NextMatchContract
import com.example.ddiff.myfootballclubapi.mvp.presenter.nextmatch.NextMatchPresenter
import com.example.ddiff.myfootballclubapi.mvp.ui.activity.DetailActivity
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity


/**
 * Created by Diffa Dwi Desyawan on 15/9/18.
 * email : diffadwi1@gmail.com.
 */
class NextMatchFragment : Fragment(), NextMatchContract.View, MatchAdapter.OnItemClickListener {

    private lateinit var presenter: NextMatchPresenter
    private var rvMatch: RecyclerView? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh:SwipeRefreshLayout
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mRootView = inflater.inflate(R.layout.fragment_next_match, container, false)
        rvMatch = mRootView.findViewById(R.id.rv_fragment_next_match)
        progressBar = mRootView.findViewById(R.id.progressBar)
        swipeRefresh = mRootView.findViewById(R.id.swipeNextMatch)
        swipeRefresh.setColorSchemeColors(Color.BLUE)
        swipeRefresh.onRefresh {
            loadPresenter()
        }
        loadPresenter()
        return mRootView
    }

    private fun loadPresenter() {
        val request = ApiService()
        val scheduler = AppSchedulerProvider()
        presenter = NextMatchPresenter(this,request,scheduler)
        presenter.getNextMatch()
    }

    override fun onLoadClub(list: List<MatchModel>) {
        val adapter = MatchAdapter(list, this)
        rvMatch!!.layoutManager = LinearLayoutManager(activity)
        rvMatch!!.adapter = adapter
    }

    override fun showProgress() {
        progressBar.visible()
    }

    override fun showLoadErrorMessage(throwable: Throwable?) {
        Toast.makeText(context, "Failed to display list. \n ${throwable?.localizedMessage}", Toast.LENGTH_LONG).show()
    }

    override fun hideProgress() {
        progressBar.invisible()
        swipeRefresh.isRefreshing =false
    }

    override fun itemDetail(model: MatchModel) {
        startActivity<DetailActivity>(getKeyIntent() to model, getKeyActivity() to "match")
    }

}

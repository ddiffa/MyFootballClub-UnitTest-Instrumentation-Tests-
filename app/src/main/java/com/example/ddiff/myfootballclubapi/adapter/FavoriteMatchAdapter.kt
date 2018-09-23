package com.example.ddiff.myfootballclubapi.adapter

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.db.FavoriteMatch
import com.example.ddiff.myfootballclubapi.utils.getSimpleDate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_match.view.*

/**
 * Created by Diffa Dwi Desyawan on 21/9/18.
 * email : diffadwi1@gmail.com.
 */
class FavoriteMatchAdapter(private val list: List<FavoriteMatch>, fragment: Fragment)
    : RecyclerView.Adapter<FavoriteMatchAdapter.ViewHolder>() {

    private val listener: FavoriteMatchAdapter.OnItemClickListener

    init {
        this.listener = fragment as FavoriteMatchAdapter.OnItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
        holder.itemView.setOnClickListener {
            listener.itemDetail(list.get(position))
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindItem(match: FavoriteMatch) {
            itemView.tv_home_club.text = match.strHomeTeam
            itemView.tv_away_club.text = match.strAwayTeam
            itemView.tv_score_home_club.text = match.intHomeScore
            itemView.tv_score_away_club.text = match.intAwayScore
            itemView.tv_date.text = getSimpleDate(match.strDate)
        }
    }

    interface OnItemClickListener {
        fun itemDetail(model: FavoriteMatch)
    }
}
package com.example.ddiff.myfootballclubapi.adapter

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ddiff.myfootballclubapi.R.layout.item_match
import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
import com.example.ddiff.myfootballclubapi.utils.getSimpleDate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_match.view.*

/**
 * Created by Diffa Dwi Desyawan on 15/9/18.
 * email : diffadwi1@gmail.com.
 */
class MatchAdapter(private val list: List<MatchModel>, fragment: Fragment)
    : RecyclerView.Adapter<MatchAdapter.Holder>() {

    private val listener: MatchAdapter.OnItemClickListener

    init {
        this.listener = fragment as MatchAdapter.OnItemClickListener
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(item_match, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindItem(list[position])
        holder.itemView.setOnClickListener {
            listener.itemDetail(list.get(position))
        }
    }


    class Holder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(items: MatchModel) {
            itemView.tv_home_club.text = items.strHomeTeam
            itemView.tv_away_club.text = items.strAwayTeam
            itemView.tv_score_home_club.text = items.intHomeScore
            itemView.tv_score_away_club.text = items.intAwayScore
            itemView.tv_date.text = getSimpleDate(items.strDate)
        }

    }

    interface OnItemClickListener {
        fun itemDetail(model: MatchModel)
    }
}
package org.d3if4304.umkmconnect.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.d3if4304.umkmconnect.AboutFragment

import org.d3if4304.umkmconnect.R
import org.d3if4304.umkmconnect.menupage.ChatProfile
import org.d3if4304.umkmconnect.menupage.HomeDetail
import org.d3if4304.umkmconnect.model.Trader

class TraderAdapter(private val context: Context,private val traderList:ArrayList<Trader>):
    RecyclerView.Adapter<TraderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TraderAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trader_row,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TraderAdapter.ViewHolder, position: Int) {
        val trader = traderList[position]
        holder.txtTraderName.text = trader.nameTrader
        holder.txtTraderAddress.text = trader.addressTrader
        holder.txtTraderDesc.text = trader.descTrader

        holder.touchTrader.setOnClickListener()
        {
            val goDetail = Intent(context, HomeDetail::class.java)

            goDetail.putExtra("nameTrader",trader.nameTrader)
            goDetail.putExtra("addressTrader",trader.addressTrader)
            goDetail.putExtra("descTrader",trader.descTrader)
            context.startActivity(goDetail)
        }
    }

    override fun getItemCount(): Int {
        return traderList.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val txtTraderName: TextView = view.findViewById(R.id.trader_Name)
        val txtTraderAddress: TextView = view.findViewById(R.id.trader_Address)
        val txtTraderDesc: TextView = view.findViewById(R.id.trader_Desc)
        val touchTrader: LinearLayout = view.findViewById(R.id.touch_Trader)
    }

}
package com.leif2k.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leif2k.shoppinglist.R

class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
    val tvCount = itemView.findViewById<TextView>(R.id.tvCount)
}
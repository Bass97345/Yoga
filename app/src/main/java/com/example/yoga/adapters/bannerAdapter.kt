package com.example.yoga.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yoga.API.Quote
import com.example.yoga.R
import com.squareup.picasso.Picasso

class bannerAdapter(private val Banners: List<Quote>) : RecyclerView.Adapter<bannerAdapter.BannerViewHolder>() {

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.Title)
        val image = itemView.findViewById<ImageView>(R.id.BannerImg)
        val description	 = itemView.findViewById<TextView>(R.id.opis)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.banner_in_main, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
    holder.title.text = Banners[position].title
        holder.description.text = Banners[position].description


        Picasso.Builder(holder.image.context)
            .build()
            .load(Banners[position].image)
            .into(holder.image)
    }

    override fun getItemCount(): Int = Banners.size
}
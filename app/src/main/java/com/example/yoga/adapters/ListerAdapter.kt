package com.example.yoga.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.yoga.API.Feelings
import com.example.yoga.R
import com.squareup.picasso.Picasso

class ListerAdapter (private val Listers: List<Feelings>) : RecyclerView.Adapter<ListerAdapter.ListerViewHolder>() {

    class ListerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.Title2)
        val image = itemView.findViewById<ImageView>(R.id.ListImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_in_main, parent, false)
        return ListerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListerViewHolder, position: Int) {
        holder.title.text = Listers[position].title

        Picasso.Builder(holder.image.context)
            .build()
            .load(Listers[position].image)
            .into(holder.image)

    }

    override fun getItemCount(): Int = Listers.size
}

package com.example.yoga.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yoga.R


class ImagenAdapter(private val uris: MutableList<Uri?>, private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClick:((Uri?) -> Unit)?= null
    var onItemClick2:((Uri?) -> Unit)?= null



    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val dateTextView: TextView = itemView.findViewById(R.id.date_text_view)
        init {
            imageView.setOnClickListener{
                onItemClick?.invoke(uris[adapterPosition])
            }
        }

    }



    inner class PlusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.Plaas)
        init {
            imageView.setOnClickListener{
                onItemClick2?.invoke(uris[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            1 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
                ImageViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_photo, parent, false)
                PlusViewHolder(view)
            }
        }

    }

    fun addImage(uri: Uri) {
        uris.add(0, uri)
        notifyDataSetChanged()  // Перезагружаем адаптер для обновления даты
    }

    override fun getItemCount(): Int = uris.size



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){
            is PlusViewHolder -> {

            }
            is ImageViewHolder -> {
                holder.imageView.setImageURI(uris[position])
                val fileName = uris[position]?.lastPathSegment
                val sharedPref = context.getSharedPreferences("image_dates", Context.MODE_PRIVATE)
                val dateString = sharedPref.getString(fileName, "Дата неизвестна")
                holder.dateTextView.text = dateString

            }
        }


    }

    override fun getItemViewType(position: Int): Int {
        return when(uris[position]){
            is Uri -> 1
            else->0

        }
    }

}






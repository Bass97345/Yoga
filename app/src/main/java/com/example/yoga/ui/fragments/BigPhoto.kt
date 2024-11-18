package com.example.yoga.ui.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.yoga.R
import com.squareup.picasso.Picasso


class BigPhoto : Fragment(R.layout.fragment_big_photo){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bigPhotoImageView = view.findViewById<ImageView>(R.id.bigPhoto)

        // Получаем переданный URI
        val photoUri = arguments?.getString("photo_uri")
        photoUri?.let {
            val uri = Uri.parse(it)

            // Создаем экземпляр Picasso и загружаем изображение
            val picasso = Picasso.Builder(requireContext())
                .build()
            picasso.load(uri)
                .into(bigPhotoImageView)
        }

        // Закрытие фрагмента по нажатию на текст "Закрыть"
        val closeTextView = view.findViewById<TextView>(R.id.close)
        closeTextView.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}


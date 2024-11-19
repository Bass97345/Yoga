package com.example.yoga.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.yoga.R
import com.squareup.picasso.Picasso
import java.io.File


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



        val deleteButton = view.findViewById<TextView>(R.id.delet)
        deleteButton.setOnClickListener {
            photoUri?.let {
                val uri = Uri.parse(it)

                // Удаляем файл изображения
                val deleted = deletePhotoFromInternalStorage(uri)
                if (deleted) {
                    // Уведомляем фрагмент Profile о том, что изображение было удалено
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(
                        "photo_deleted", uri.toString()
                    )
                }

                // Возвращаемся к предыдущему экрану
                findNavController().popBackStack()
            }
        }
    }

    private fun deletePhotoFromInternalStorage(uri: Uri): Boolean {
        val file = File(uri.path ?: return false)
        return if (file.exists()) {
            file.delete()
        } else false
    }
}



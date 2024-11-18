package com.example.yoga.ui.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yoga.API.User
import com.example.yoga.API.dataStore
import com.example.yoga.R
import com.example.yoga.adapters.ImagenAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class Profile : Fragment(R.layout.fragment_profile) {


    fun getUserData(): Flow<User> {
        return requireContext().dataStore.data.map { settings ->
            User(
                id = settings[stringPreferencesKey("id")] ?: "",
                email = settings[stringPreferencesKey("email")] ?: "",
                nickName = settings[stringPreferencesKey("nickName")] ?: "",
                avatar = settings[stringPreferencesKey("avatar")] ?: "",
                token = settings[stringPreferencesKey("token")] ?: ""
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val HomeButton = view.findViewById<Button>(R.id.HomeBtn)

        HomeButton.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_main2)
        }





        fun getImageFromInternalStorage():List<Uri> {
            val directory = context?.getDir("images", Context.MODE_PRIVATE)
            val files = directory?.listFiles()
            val mmutableList = mutableListOf<Uri>()

            if (files != null) {
                for (file in files) {
                    val uri = Uri.fromFile(file)
                    mmutableList.add(uri)
                }
            }
            return mmutableList
        }



        val banner = mutableListOf<Uri?>(
            null
        )

        banner.addAll(0, getImageFromInternalStorage())

        val sampledRecyclerViewImagen = view.findViewById<RecyclerView>(R.id.ImagenRecyclerViewList)
        val ImagenAdapter = ImagenAdapter(banner, requireContext())
        val gridLayoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        sampledRecyclerViewImagen.layoutManager = gridLayoutManager
        sampledRecyclerViewImagen.adapter = ImagenAdapter


        fun saveImageToInternalStorage(uri: Uri) {
            val context = requireContext()
            val contentResolver = context.contentResolver
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))

            val directory = context.getDir("images", Context.MODE_PRIVATE)
            val file = File(directory, "${UUID.randomUUID()}.png")
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()

            // Сохранение даты создания
            val currentTime = System.currentTimeMillis()
            val dateString = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(currentTime))
            val sharedPref = context.getSharedPreferences("image_dates", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString(file.name, dateString)  // Сохраняем дату по имени файла
                apply()
            }

            // Добавляем только одно изображение в адаптер
            val imageUri = Uri.fromFile(file)
            banner.add(0, imageUri) // Добавляем в список banner
            // Обновляем адаптер
        }



        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                saveImageToInternalStorage(uri)  // Только сохраняем и обновляем адаптер внутри этого метода
                ImagenAdapter.notifyDataSetChanged()
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }


        ImagenAdapter.onItemClick = { uri ->
            uri?.let {
                val bundle = Bundle().apply {
                    putString("photo_uri", it.toString()) // Передаем URI выбранной фотографии
                }
                findNavController().navigate(R.id.action_profile_to_bigPhoto2, bundle)
            }
        }


        ImagenAdapter.onItemClick2={
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }




        val menuBtn = view.findViewById<ImageView>(R.id.menuBtnProf)

        menuBtn.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("source", "profile")
            findNavController().navigate(R.id.action_profile_to_menu, bundle)
        }

        val listenBtn = view.findViewById<ImageButton>(R.id.listen_btn)

        listenBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("source", "profile")
            findNavController().navigate(R.id.action_profile_to_listener, bundle)}


        val avatarImageView = view.findViewById<ImageView>(R.id.Avatar)

        val nameTextView = view.findViewById<TextView>(R.id.ProfileName)

        lifecycleScope.launch {
            val user = getUserData().first()
            val avatarUrl = user.avatar
            val nameA = user.nickName
            if (avatarUrl.isNotEmpty()) {

                val picasso = Picasso.Builder(requireContext())
                    .build()
                picasso
                    .load(avatarUrl)
                    .into(avatarImageView)

            }

            if(nameA.isNotEmpty()){
                nameTextView.text = nameA
            }
        }


        suspend fun clearData() {
            requireContext().dataStore.edit { settings ->
                settings.remove(stringPreferencesKey("id"))
                settings.remove(stringPreferencesKey("email"))
                settings.remove(stringPreferencesKey("nickName"))
                settings.remove(stringPreferencesKey("avatar"))
                settings.remove(stringPreferencesKey("token"))
            }
        }

        val exitBtn = view.findViewById<TextView>(R.id.exitButton)

        exitBtn.setOnClickListener {
            lifecycleScope.launch {
                clearData()

            }
            findNavController().navigate(R.id.action_profile_to_onboarding)
        }

    }

}


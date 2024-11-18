package com.example.yoga.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yoga.API.RetrofitClient
import com.example.yoga.API.User
import com.example.yoga.API.dataStore
import com.example.yoga.R
import com.example.yoga.adapters.ListerAdapter
import com.example.yoga.adapters.bannerAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class Main : Fragment(R.layout.fragment_main) {

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

        val sampleRecyclerView = view.findViewById<RecyclerView>(R.id.SampleRecyclerViewList)

        val sampledRecyclerViewLister = view.findViewById<RecyclerView>(R.id.SampledRecyclerViewList)


        val profileBtn = view.findViewById<ImageButton>(R.id.porofile)

        profileBtn.setOnClickListener{
            findNavController().navigate(R.id.action_main2_to_profile)
        }

        val menuBtn = view.findViewById<ImageButton>(R.id.menu_main_btn)

        menuBtn.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("source", "main")
            findNavController().navigate(R.id.action_main2_to_menu, bundle)
        }


        val listenBtn = view.findViewById<ImageButton>(R.id.listen_btn)

    listenBtn.setOnClickListener {
        val bundle = Bundle()
        bundle.putString("source", "main")
    findNavController().navigate(R.id.action_main2_to_listener, bundle)}



        val api = RetrofitClient.getMadInstance()
        lifecycleScope.launch {
            val result = api.getQuotes()
            val adapter = bannerAdapter(result.body()!!.data)
            sampleRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            sampleRecyclerView.adapter = adapter

            val result2 = api.getFeelings()
            val ListerAdapter = ListerAdapter(result2.body()!!.data)
            sampledRecyclerViewLister.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            sampledRecyclerViewLister.adapter = ListerAdapter

            val avatarImageView = view.findViewById<ImageView>(R.id.miniAva)

            val nameTextView = view.findViewById<TextView>(R.id.NameInmain)

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




    }


}

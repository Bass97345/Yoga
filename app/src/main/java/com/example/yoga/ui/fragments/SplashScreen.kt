package com.example.yoga.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.yoga.API.User
import com.example.yoga.API.dataStore
import com.example.yoga.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class SplashScreen : Fragment(R.layout.fragment_splash_screen) {



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
        lifecycleScope.launch {
            val user = getUserData().first()
            if(user.token != ""){
                println("collect")
                findNavController().navigate(R.id.action_splashScreen_to_main2)
            }else{findNavController().navigate(R.id.action_splashScreen_to_onboarding2)}

        }






    }

}








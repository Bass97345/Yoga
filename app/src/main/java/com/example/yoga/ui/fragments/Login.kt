package com.example.yoga.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.yoga.API.LoginRequest
import com.example.yoga.API.RetrofitClient
import com.example.yoga.API.User
import com.example.yoga.API.dataStore
import com.example.yoga.R
import kotlinx.coroutines.launch


class Login : Fragment(R.layout.fragment_login) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val emailEditText = view.findViewById<EditText>(R.id.Email)
        val passwordEditText = view.findViewById<EditText>(R.id.Password)
        val singUpButton = view.findViewById<Button>(R.id.SingInButton)

// Загружаем email последнего пользователя из SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val savedEmail = sharedPreferences.getString("LAST_EMAIL", null)

// Если email был сохранен, подставляем его в поле Email
        if (savedEmail != null) {
            emailEditText.setText(savedEmail)
        }

        singUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Проверка на пустоту полей и наличие @ в email
            if (email.isEmpty()) {
                emailEditText.error = "Поле электронной почты не должно быть пустым"
                return@setOnClickListener
            }

            if (!email.contains("@")) {
                emailEditText.error = "Электронная почта должна содержать символ @"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordEditText.error = "Поле пароля не должно быть пустым"
                return@setOnClickListener
            }

            // Если проверки пройдены, отправляем запрос на сервер
            val api = RetrofitClient.getMadInstance()
            lifecycleScope.launch {
                val result = api.postUser(LoginRequest(email, password))
                if (result.isSuccessful) {
                    val responseBody = result.body()
                    saveData(responseBody)

                    // Сохраняем email в SharedPreferences после успешной авторизации
                    sharedPreferences.edit().putString("LAST_EMAIL", email).apply()

                    findNavController().navigate(R.id.action_login_to_main2)
                }
            }
        }


    }

        suspend fun saveData(responseBody: User?) {
            requireContext().dataStore.edit { settings ->
                settings[stringPreferencesKey("id")] = responseBody?.id ?: ""
                settings[stringPreferencesKey("email")] = responseBody?.email ?: ""
                settings[stringPreferencesKey("nickName")] = responseBody?.nickName ?: ""
                settings[stringPreferencesKey("avatar")] = responseBody?.avatar ?: ""
                settings[stringPreferencesKey("token")] = responseBody?.token ?: ""
            }
        }
}




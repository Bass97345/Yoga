package com.example.yoga.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.yoga.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Onboarding : Fragment(R.layout.fragment_onboarding) {
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val regBtn = view.findViewById<TextView>(R.id.regBtn)
            val buttonSign = view.findViewById<Button>(R.id.angry_btn)


            buttonSign.setOnClickListener {
                findNavController().navigate(R.id.action_onboarding_to_login2)

            }

            regBtn.setOnClickListener{
                findNavController().navigate(R.id.action_onboarding_to_regestration)
            }

        }
    }

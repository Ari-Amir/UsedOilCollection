package com.aco.usedoilcollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aco.usedoilcollection.MainActivity
import com.aco.usedoilcollection.R
import com.aco.usedoilcollection.viewmodel.AuthViewModel

class AccountFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)

        val usernameTextView = view.findViewById<TextView>(R.id.username)
        val emailTextView = view.findViewById<TextView>(R.id.email)
        val logoutButton = view.findViewById<Button>(R.id.logout_button)

        val user = authViewModel.getCurrentUser()

        user?.let {
            usernameTextView.text = it.name
            emailTextView.text = it.email
        }

        logoutButton.setOnClickListener {
            user?.id?.let { userId ->
                authViewModel.logout(userId)
                (activity as MainActivity).navigateToAuthScreen()
            }
        }
    }
}

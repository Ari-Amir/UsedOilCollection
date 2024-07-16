package com.aco.usedoilcollection

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.aco.usedoilcollection.database.AppDatabase
import com.aco.usedoilcollection.repository.LocationRepository
import com.aco.usedoilcollection.repository.OilCollectionRepository
import com.aco.usedoilcollection.repository.UserRepository
import com.aco.usedoilcollection.viewmodel.*
import kotlinx.coroutines.launch
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2
import com.aco.usedoilcollection.ui.ViewPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var authViewModel: AuthViewModel
    private lateinit var oilCollectionViewModel: OilCollectionViewModel
    private lateinit var locationViewModel: LocationViewModel
    private var isUserLoggedIn: Boolean = false
    var currentUserId: Int? = null
    private var currentUserEmail: String? = null
    lateinit var locationRepository: LocationRepository
    lateinit var oilCollectionRepository: OilCollectionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "oil_collection_database"
        ).build()

        locationRepository = LocationRepository(database.locationDao())
        oilCollectionRepository = OilCollectionRepository(database.oilCollectionRecordDao())
        val oilCollectionRepository = OilCollectionRepository(database.oilCollectionRecordDao())
        val locationRepository = LocationRepository(database.locationDao())
        val userRepository = UserRepository(database.userDao())

        authViewModel = ViewModelProvider(this, AuthViewModelFactory(userRepository)).get(AuthViewModel::class.java)
        oilCollectionViewModel = ViewModelProvider(this, OilCollectionViewModelFactory(oilCollectionRepository)).get(OilCollectionViewModel::class.java)
        locationViewModel = ViewModelProvider(this, LocationViewModelFactory(locationRepository))[LocationViewModel::class.java]

        lifecycleScope.launch {
            try {
                val loggedInUser = userRepository.getLoggedInUser()
                if (loggedInUser != null) {
                    isUserLoggedIn = true
                    currentUserEmail = loggedInUser.email
                    currentUserId = loggedInUser.id
                    authViewModel.setCurrentUser(loggedInUser)
                    oilCollectionViewModel.setCurrentUser(loggedInUser.id)
                }
            } catch (e: Exception) {
                // Logs
            }

            if (isUserLoggedIn) {
                showViewPagerScreen()
            } else {
                setupAuthScreen()
            }
        }
    }

    private fun setupAuthScreen() {
        setContentView(R.layout.auth_screen)

        val emailEditText = findViewById<EditText>(R.id.email_field)
        val passwordEditText = findViewById<EditText>(R.id.password_field)
        val nameEditText = findViewById<EditText>(R.id.name_field)
        val authButton = findViewById<Button>(R.id.auth_button)
        val toggleButton = findViewById<Button>(R.id.toggle_button)
        val errorTextView = findViewById<TextView>(R.id.error_message)

        var isLoginMode = true

        toggleButton.setOnClickListener {
            isLoginMode = !isLoginMode
            nameEditText.visibility = if (isLoginMode) View.GONE else View.VISIBLE
            authButton.text = if (isLoginMode) "Sign In" else "Create Account"
            toggleButton.text = if (isLoginMode) "Don't have an account? Create Account" else "Already have an account? Sign In"
        }

        authButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val name = nameEditText.text.toString()

            when {
                email.isEmpty() || password.isEmpty() || (!isLoginMode && name.isEmpty()) -> {
                    errorTextView.text = "All fields are required"
                    errorTextView.visibility = View.VISIBLE
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    errorTextView.text = "Invalid email address"
                    errorTextView.visibility = View.VISIBLE
                }
                else -> {
                    errorTextView.visibility = View.GONE
                    if (isLoginMode) {
                        authViewModel.login(email, password, onSuccess = {
                            currentUserId = authViewModel.getCurrentUser()?.id
                            currentUserId?.let { oilCollectionViewModel.setCurrentUser(it) }
                            showViewPagerScreen()
                        }) { error ->
                            errorTextView.text = error
                            errorTextView.visibility = View.VISIBLE
                        }
                    } else {
                        authViewModel.register(email, password, name, onSuccess = {
                            currentUserId = authViewModel.getCurrentUser()?.id
                            currentUserId?.let { oilCollectionViewModel.setCurrentUser(it) }
                            showViewPagerScreen()
                        }) { error ->
                            errorTextView.text = error
                            errorTextView.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun showViewPagerScreen() {
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Input"
                1 -> "Statistics"
                2 -> "Locations"
                3 -> "Account"
                else -> null
            }
        }.attach()
    }

    fun navigateToAuthScreen() {
        setupAuthScreen()
    }
}

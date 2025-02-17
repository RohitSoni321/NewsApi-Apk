package com.example.newsapiapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newsapiapp.databinding.ActivityMainBinding
import com.example.newsapiapp.presentation.adapter.NewsAdapter
import com.example.newsapiapp.presentation.adapter.SavedNewsAdapter
import com.example.newsapiapp.presentation.viewModel.NewsViewModel
import com.example.newsapiapp.presentation.viewModel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var newsViewModelFactory: NewsViewModelFactory
    @Inject
    lateinit var newsAdapter: NewsAdapter
    @Inject
    lateinit var savedNewsAdapter: SavedNewsAdapter
    lateinit var newsViewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup bottom navigation with NavController
        binding.bottomNav.setupWithNavController(navController)

        newsViewModel = ViewModelProvider(this,newsViewModelFactory).get(NewsViewModel::class.java)

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.newsFragment -> {
                    navController.navigate(R.id.newsFragment)
                    true
                }
                R.id.savedNewsFragment -> {
                    navController.navigate(R.id.savedNewsFragment)
                    true
                }
                else -> false
            }
        }

    }


}
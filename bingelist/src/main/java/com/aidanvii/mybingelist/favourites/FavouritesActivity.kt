package com.aidanvii.mybingelist.favourites

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.aidanvii.mybingelist.App
import com.aidanvii.mybingelist.R
import com.aidanvii.mybingelist.databinding.ActivityFavouritesBinding

class FavouritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouritesBinding

    private val favouritesComponent by lazy { App.appComponent.favouritesComponent }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourites)
        binding.viewModel = favouritesComponent.favouritesListViewModel
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }
}
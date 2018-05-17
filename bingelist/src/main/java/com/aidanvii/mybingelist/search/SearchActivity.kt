package com.aidanvii.mybingelist.search

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aidanvii.mybingelist.App
import com.aidanvii.mybingelist.R
import com.aidanvii.mybingelist.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val searchComponent by lazy { App.appComponent.searchComponent }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchComponent.navigator.sourceActivity = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.viewModel = searchComponent.searchListViewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        searchComponent.navigator.sourceActivity = null
    }
}
package com.example.submissionakhir.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionakhir.R
import com.example.submissionakhir.databinding.ActivityMainBinding
import com.example.submissionakhir.network.response.DataItems
import com.example.submissionakhir.util.ThemeSettingPreferences
import com.example.submissionakhir.viewmodel.SettingViewModel
import com.example.submissionakhir.viewmodel.SettingViewModelFactory
import com.example.submissionakhir.adapter.HomeAdapter
import com.example.submissionakhir.viewmodel.HomeViewModel

class MainActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(SETTING)
    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root )
        supportActionBar?.title= getString(R.string.github_user_search)
        viewModel.getListUsers("z")
        val pref = ThemeSettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }



        with(binding) {
            etSearch.setupWithSearchBar(itemSearchView)
            etSearch.editText.setOnEditorActionListener { _, _, _ ->
                itemSearchView.setText(etSearch.text)
                etSearch.hide()
                viewModel.getListUsers(etSearch.text.toString())
                viewModel.listUser.observe(this@MainActivity) {
                    if (it.isNullOrEmpty()) {
                        findUser(false)

                    } else {
                        findUser(true)
                    }
                }
                false
            }

            if (etSearch.text.isEmpty()) {
                findUser(true)
            } else {
                findUser(false)
            }
        }


        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvUserGit.layoutManager = layoutManager

        viewModel.listUser.observe(this) {
            if (it != null) {
                listUserGithub(it)
            }
        }

    }
    private fun listUserGithub(data: List<DataItems>) {
        val adapter = HomeAdapter(object : HomeAdapter.OnClickListener{
            override fun itemClick(data: DataItems) {
                navigatoToCourse(data)
            }
        })
        adapter.sendCategory(data)
        binding.rvUserGit.adapter = adapter
    }
    private fun showLoading(isLoading: Boolean){
        binding.apply {
            if (isLoading){
                prgres.visibility = View.VISIBLE
            } else {
                prgres.visibility = View.GONE
            }
        }
    }
    private fun findUser(userFound: Boolean) {
        binding.apply {
            if (userFound){
                rvUserGit.visibility = View.VISIBLE
                tvNotFound.visibility = View.GONE
            } else{
                rvUserGit.visibility = View.GONE
                tvNotFound.visibility = View.VISIBLE

            }
        }
    }
    private fun navigatoToCourse(data: DataItems){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(ARG_LOGIN, data.login)
        startActivity(intent)
        Log.d("tester", "todetail $intent")
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)

            }
            R.id.favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)


    }

    companion object {
        const val ARG_LOGIN = "login"
        const val SETTING = "settings"
    }

}
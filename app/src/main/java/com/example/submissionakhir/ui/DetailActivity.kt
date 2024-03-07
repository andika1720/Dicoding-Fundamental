package com.example.submissionakhir.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.submissionakhir.ui.MainActivity.Companion.ARG_LOGIN
import com.example.submissionakhir.R
import com.example.submissionakhir.database.Favorite
import com.example.submissionakhir.databinding.ActivityDetailBinding
import com.example.submissionakhir.viewmodel.DetailViewModel
import com.example.submissionakhir.viewmodel.ViewModelFactory
import com.example.submissionakhir.adapter.AdapterSectionPagerForDetail
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private lateinit var userFavorite: Favorite

    private val viewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(application )
    }
    @SuppressLint("SetTextI18n", "StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title= getString(R.string.detail_user)

        val getKey = intent.getStringExtra(ARG_LOGIN)

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        if (getKey != null){
            val adapterPager = AdapterSectionPagerForDetail(this, getKey)
            binding.apply {
                viewPager2Course.adapter = adapterPager
                TabLayoutMediator(tabLayoutDetailCourse, viewPager2Course) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }

        if (getKey != null){
            viewModel.getDetailUser(getKey)
        }
        viewModel.detailUser.observe(this) {
            if (it != null) {
                binding.tvNameuser.text = it.name
                binding.tvUsernameDetail.text = it.login
                binding.bio.text = it.bio.toString()
                binding.tvFollowers.text = getString(R.string.followers_count, it.followers)
                binding.tvFollowing.text = getString(R.string.following_count, it.following)
                Glide.with(this@DetailActivity)
                    .load(it.avatarUrl)
                    .fitCenter()
                    .into(binding.ivProfile)


                userFavorite = Favorite(it.login, it.avatarUrl)
                viewModel.isFavorite(userFavorite.username).observe(this) { it1 ->
                    setIsFavorite(it1)
                }
            }
        }

        binding.fabAddFavo.apply {
            setOnClickListener {
                if (tag == TAG_ADD){
                    viewModel.deleteFavorite(userFavorite)
                } else {
                    viewModel.insertFavorite(userFavorite)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressbarDetail.visibility = View.VISIBLE
        }else {
            binding.progressbarDetail.visibility = View.GONE
        }

    }

    private fun setIsFavorite(favo: Boolean){
        binding.fabAddFavo.apply {
            if (favo){
                tag = TAG_ADD
                setImageDrawable(
                    ContextCompat.getDrawable(
                    this@DetailActivity,R.drawable.baseline_favorite_24)
                )
            } else {
                tag = ""
                setImageDrawable(
                    ContextCompat.getDrawable(
                    this@DetailActivity,R.drawable.baseline_favorite_border_24)
                )
            }
        }
    }
    companion object{
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following

        )
        private val TAG_ADD = R.string.add

    }
}
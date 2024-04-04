package com.example.submissiongithub

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.submissiongithub.UserAdapter.Companion.EXTRA_HTML
import com.example.submissiongithub.UserAdapter.Companion.EXTRA_URL
import com.example.submissiongithub.UserAdapter.Companion.EXTRA_USERNAME
import com.example.submissiongithub.data.response.DetailResponse
import com.example.submissiongithub.database.Favorite
import com.example.submissiongithub.databinding.DetailUserBinding
import com.example.submissiongithub.helper.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {


    private lateinit var binding: DetailUserBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedData = intent.getStringExtra(EXTRA_USERNAME) ?: "tes"
        val receivedURL = intent.getStringExtra(EXTRA_URL) ?: "sappawrdn"
        val receivedHtml = intent.getStringExtra(EXTRA_HTML) ?: "sappawrdn"


        val fragment = FollowerFragment()
        val bundle = Bundle()

        bundle.putString(FollowerFragment.ARG_USERNAME, receivedData)
        fragment.arguments = bundle

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = receivedData


        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()



        supportActionBar?.elevation = 0f

        supportActionBar?.hide()

        detailViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(application))
            .get(DetailViewModel::class.java)

        favoriteViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(application))
            .get(FavoriteViewModel::class.java)

        val fabFav: FloatingActionButton = binding.floatingButton

        binding.floatingButtonShare.setOnClickListener {
            shareContent(receivedHtml)
        }

        favoriteViewModel.getFavorite(receivedData).observe(this) { favorite ->
            if (favorite != null) {
                binding.floatingButton.setImageResource(R.drawable.favorite_fill)

                binding.floatingButton.setOnClickListener {
                    favoriteViewModel.delete(favorite)
                }
            } else {
                binding.floatingButton.setImageResource(R.drawable.favorite_border)

                binding.floatingButton.setOnClickListener {
                    val newFavorite = Favorite(receivedData, receivedURL)
                    favoriteViewModel.insert(newFavorite)
                }
            }

        }

        fabFav.setOnClickListener {
            val favorite = Favorite(receivedData, receivedURL)
            favoriteViewModel.insert(favorite)
        }

        detailViewModel.findDetail(receivedData)

        detailViewModel.detailData.observe(this) { DetailResponse ->
            DetailResponse?.let {
                showDetail(DetailResponse)
            }
        }

        detailViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

    }


    private fun showDetail(detailResponse: DetailResponse) {
        Glide.with(this)
            .load(detailResponse.avatarUrl)
            .transform(CircleCrop())
            .into(binding.ivDetail)

        binding.tvDetailname.text = detailResponse.name
        binding.tvDetailusername.text = detailResponse.login
        binding.tvFollowersnum.text = detailResponse.followers.toString()
        binding.tvFollowingnum.text = detailResponse.following.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarDetail.visibility = View.VISIBLE
        } else {
            binding.progressBarDetail.visibility = View.GONE
        }
    }

    private fun shareContent(share: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)

        shareIntent.type = "text/plain"

        shareIntent.putExtra(Intent.EXTRA_TEXT, share)

        startActivity(Intent.createChooser(shareIntent, "Bagikan Tautan Melalui"))

    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}
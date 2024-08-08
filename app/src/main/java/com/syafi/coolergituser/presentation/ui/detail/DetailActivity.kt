package com.syafi.coolergituser.presentation.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.syafi.coolergituser.R
import com.syafi.coolergituser.databinding.ActivityDetailBinding
import com.syafi.core.domain.model.GithubUser
import com.syafi.core.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()
    private var isFavoriteUser: Boolean= false
    private var userData: GithubUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user= intent.getStringExtra(DETAIL_EXTRA)

        user?.let { username ->
            observeLiveData(username)
            viewModel.isFavoriteUserExist(username).observe(this) {
                isFavoriteUser= it
                setFabIcon(it)
            }
        }

        viewModel.user.observe(this) {
            userData= it
        }

        setAppBar(user.toString())

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }


        binding.fab.setOnClickListener {
            if (isFavoriteUser) {
                if (userData != null) {
                    isFavoriteUser= false
                    binding.fab.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_not_favorite))
                    lifecycleScope.launch {
                        viewModel.deleteFavoriteUser(userData as GithubUser)
                    }
                    showToast(getString(R.string.remove_from_favorite))
                } else {
                    showToast(getString(R.string.user_not_found))
                }
            } else {
                if (userData != null) {
                    isFavoriteUser= true
                    binding.fab.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_favorite))
                    lifecycleScope.launch {
                        viewModel.addFavoriteUser(userData as GithubUser)
                    }
                    showToast(getString(R.string.user_added_to_favorite))
                } else {
                    showToast(getString(R.string.user_not_found))
                }
            }
        }
    }

    private fun setFabIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fab.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_favorite))
        } else {
            binding.fab.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_not_favorite))
        }
    }

    private fun observeLiveData(username: String) {
        lifecycleScope.launch {
            viewModel.getDetailUser(username).observe(this@DetailActivity) {
                when(it) {
                    is Resource.Error -> {
                        viewModel.setLoadingState(false)
                        showToast(it.message.toString())
                    }
                    is Resource.Loading -> viewModel.setLoadingState(true)
                    is Resource.Success -> {
                        viewModel.setLoadingState(false)
                        it.data?.let { data ->
                            viewModel.setUserData(data)
                            setViewData(data)
                        }
                    }
                }
            }

            viewModel.getFollowerCount(username).observe(this@DetailActivity) {
                binding.tvFollowersCount.text= (it ?: "0").toString()
            }
            viewModel.getFollowingCount(username).observe(this@DetailActivity) {
                binding.tvFollowingCount.text= (it ?: "0").toString()
            }
        }
    }

    private fun setViewData(data: GithubUser) {
        binding.apply {
            tvId.text= getString(R.string.user_id, data.id.toString())
            Glide.with(this@DetailActivity)
                .load(data.avatar_url)
                .into(ivAvatar)
        }
    }

    private fun setAppBar(title: String) {
        binding.apply {
            appbar.title= title
            appbar.setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) loading.visibility= View.VISIBLE
            else loading.visibility= View.GONE
        }
    }

    companion object {
        const val DETAIL_EXTRA= "detail_extra"
    }
}

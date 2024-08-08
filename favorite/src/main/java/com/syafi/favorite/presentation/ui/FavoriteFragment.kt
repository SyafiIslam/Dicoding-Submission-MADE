package com.syafi.favorite.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafi.coolergituser.presentation.di.FavoriteModule
import com.syafi.coolergituser.presentation.ui.detail.DetailActivity
import com.syafi.core.data.local.room.entity.FavoriteUser
import com.syafi.core.ui.adapter.FavoriteUserAdapter
import com.syafi.core.util.Resource
import com.syafi.favorite.DaggerFavoriteComponent
import com.syafi.favorite.databinding.FragmentFavoriteBinding
import com.syafi.favorite.presentation.ViewModelFactory
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding as FragmentFavoriteBinding

    override fun onAttach(context: Context) {
        DaggerFavoriteComponent.builder()
            .context(requireActivity())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavoriteModule::class.java
                )
            ).build().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.getAllFavoriteUser().observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Error -> {
                    viewModel.setLoading(false)
                    showToast(it.message.toString())
                }
                is Resource.Loading -> viewModel.setLoading(true)
                is Resource.Success -> {
                    viewModel.setLoading(false)
                    setRecyclerViewData(it.data as List<FavoriteUser>)
                }
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setRecyclerViewData(favoriteList: List<FavoriteUser>) {
        val adapter = FavoriteUserAdapter(favoriteList)
        binding.rvFavorite.adapter = adapter
        adapter.onItemClickCallBack(object : FavoriteUserAdapter.OnItemClickCallBack {
            override fun onItemClick(user: String) {
                val detailIntent= Intent(activity, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.DETAIL_EXTRA, user)
                startActivity(detailIntent)
            }
        })

    }

    private fun setupRecyclerView() {
        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager= LinearLayoutManager(activity)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) loading.visibility= View.VISIBLE
            else loading.visibility= View.GONE
        }
    }
}
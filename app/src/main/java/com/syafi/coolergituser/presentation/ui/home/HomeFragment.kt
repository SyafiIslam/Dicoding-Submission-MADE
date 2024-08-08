package com.syafi.coolergituser.presentation.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafi.coolergituser.databinding.FragmentHomeBinding
import com.syafi.coolergituser.presentation.ui.detail.DetailActivity
import com.syafi.core.domain.model.GithubUser
import com.syafi.core.ui.adapter.GitUserAdapter
import com.syafi.core.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        lifecycleScope.launch {
            viewModel.getAllUser().observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        viewModel.setLoadingState(false)
                        showToast(it.message.toString())
                    }
                    is Resource.Loading -> viewModel.setLoadingState(true)
                    is Resource.Success -> {
                        viewModel.setLoadingState(false)
                        setRecyclerViewData(it.data as List<GithubUser>)
                    }
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setUpRecyclerView() {
        binding.rvUser.layoutManager = LinearLayoutManager(activity)
        binding.rvUser.setHasFixedSize(true)
    }

    private fun setRecyclerViewData(userList: List<GithubUser>) {
        val adapter = GitUserAdapter(userList)
        binding.rvUser.adapter = adapter

        adapter.onIconClickCallBack(object : GitUserAdapter.OnIconClickCallBack {
            override fun onIconClick(url: String) {
                val uri = Uri.parse(url)
                val openBrowserIntent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(openBrowserIntent)
            }
        })

        adapter.onItemClickCallBack(object : GitUserAdapter.OnItemClickCallBack {
            override fun onItemClick(user: String) {
                val detailIntent= Intent(activity, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.DETAIL_EXTRA, user)
                startActivity(detailIntent)
            }
        })
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
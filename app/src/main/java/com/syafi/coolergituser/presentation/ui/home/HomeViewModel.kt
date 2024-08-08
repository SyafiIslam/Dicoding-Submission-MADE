package com.syafi.coolergituser.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syafi.core.domain.usecase.remote.GithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val githubUserUseCase: GithubUserUseCase
): ViewModel() {

    private val _isLoading= MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setLoadingState(state: Boolean) {
        _isLoading.value= state
    }

    suspend fun getAllUser()= githubUserUseCase.getAllUsers().asLiveData()
}
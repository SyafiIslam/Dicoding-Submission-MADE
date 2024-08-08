package com.syafi.favorite.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syafi.core.domain.usecase.local.FavoriteUserUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val favoriteUserUseCase: FavoriteUserUseCase
): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFavoriteUser() = favoriteUserUseCase.getAllFavoriteUser().asLiveData()

    fun setLoading(state: Boolean) {
        _isLoading.value = state
    }
}
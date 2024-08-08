package com.syafi.coolergituser.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syafi.core.domain.model.GithubUser
import com.syafi.core.domain.usecase.local.FavoriteUserUseCase
import com.syafi.core.domain.usecase.remote.GithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val githubUserUseCase: GithubUserUseCase,
    private val favoriteUserUseCase: FavoriteUserUseCase
): ViewModel() {

    private val _isLoading= MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setLoadingState(state: Boolean) {
        _isLoading.value= state
    }

    private val _user= MutableLiveData<GithubUser>()
    val user: LiveData<GithubUser> = _user

    suspend fun getDetailUser(username: String)= githubUserUseCase.getDetailUser(username).asLiveData()
    suspend fun getFollowerCount(username: String)= githubUserUseCase.getUserFollowers(username).asLiveData()
    suspend fun getFollowingCount(username: String)= githubUserUseCase.getUserFollowing(username).asLiveData()

    suspend fun addFavoriteUser(user: GithubUser) {
        favoriteUserUseCase.addFavoriteUser(user)
    }

    suspend fun deleteFavoriteUser(user: GithubUser) {
        favoriteUserUseCase.deleteFavoriteUser(user)
    }

    fun isFavoriteUserExist(username: String)= favoriteUserUseCase.isUserFavorite(username).asLiveData()

    fun setUserData(user: GithubUser) {
        _user.value= user
    }

}
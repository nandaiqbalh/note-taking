package com.nandaiqbalh.notetaking.presentation.ui.login

import android.app.Application
import androidx.lifecycle.*
import com.nandaiqbalh.notetaking.data.local.user.UserDao
import com.nandaiqbalh.notetaking.data.local.user.UserEntity
import com.nandaiqbalh.notetaking.data.repository.LocalRepository
import com.nandaiqbalh.notetaking.wrapper.Resource
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LocalRepository) : ViewModel(){

    private var _getUserResult = MutableLiveData<Resource<UserEntity>>()
    val getUser: LiveData<Resource<UserEntity>> get() = _getUserResult

    fun getUser(username: String) {
        viewModelScope.launch {
            _getUserResult.postValue(repository.getUser(username))
        }
    }
}
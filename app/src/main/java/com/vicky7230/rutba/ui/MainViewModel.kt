package com.vicky7230.rutba.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vicky7230.rutba.network.NetworkLayer
import com.vicky7230.rutba.network.SlackHttpLayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.liveData
import com.vicky7230.rutba.network.Resource
import timber.log.Timber

class MainViewModel : ViewModel() {

    private val data = MutableLiveData<String>()

    fun test() {
        viewModelScope.launch(Dispatchers.IO) {
            SlackHttpLayer.test()
        }
    }

    fun getAuthToken(code: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = NetworkLayer.getAuthToken(code)))
        } catch (exception: Exception) {
            Timber.e(exception)
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getUserInfo(auth: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = NetworkLayer.getUserInfo(auth)))
        } catch (exception: Exception) {
            Timber.e(exception)
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getUserPresence(auth: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = NetworkLayer.getUserPresence(auth)))
        } catch (exception: Exception) {
            Timber.e(exception)
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
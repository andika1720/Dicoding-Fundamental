package com.example.submissionakhir.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionakhir.network.response.DataItems
import com.example.submissionakhir.network.response.ResponseList

import com.example.submissionakhir.network.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel: ViewModel() {
    companion object {
        private const val TAG = "errorHOME"
    }
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUser = MutableLiveData<List<DataItems>>()
    val listUser: LiveData<List<DataItems>> = _listUser


    fun getListUsers(userListGithub: String?) {
        _isLoading.value = true
        val config = ApiConfig.instance.getUsers(userListGithub)
        config.enqueue(object : Callback<ResponseList> {
            override fun onResponse(call: Call<ResponseList>, response: Response<ResponseList>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseList>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}
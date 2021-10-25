package com.example.mvvm_room_repository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_room_repository.databinding.ActivityMainBinding
import com.example.mvvm_room_repository.room.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        /**
         * ViewModel은 공유 가능합니다. 만약 activity의 ViewModel을 Fragment에서도 쓰고 싶다면, 아래와 같이 사용할 수 있습니다.
         */

    }


}
package com.example.mvvm_room_repository

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.mvvm_room_repository.databinding.UserDialogBinding
import com.example.mvvm_room_repository.room.entity.UserEntity
import com.example.mvvm_room_repository.room.viewmodel.MainViewModel
import java.util.*

class UserDialog(mContext: Context) : Dialog(mContext) {
    private lateinit var binding: UserDialogBinding

    private val viewModel: MainViewModel = MainViewModel(mContext.applicationContext as Application)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.user_dialog)
        binding = UserDialogBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 다이얼로그의 배경을 투명으로 만든다.
        Objects.requireNonNull(window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // 버튼 리스너 설정
        binding.buttonSave.setOnClickListener {
            // '확인' 버튼 클릭시 data insert
            viewModel.insert(
                UserEntity(
                    binding.userName.text.toString(),   // 이름
                    binding.userGender.text.toString(), // 성별
                    binding.userBirth.text.toString()   // 나이
                )
            )
            // Custom Dialog 종료
            dismiss()
        }
        binding.buttonCancel.setOnClickListener {
            // '취소' 버튼 클릭시
            // Custom Dialog 종료
            dismiss()
        }
    }
}
package com.example.mvvm_room_repository

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_room_repository.room.entity.UserEntity

class UserListAdapter internal constructor(context: Context)
    : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var users = emptyList<UserEntity>() // Cached copy of words

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val userGender: TextView = itemView.findViewById(R.id.gender)
        val userBirth: TextView = itemView.findViewById(R.id.birth)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current = users[position]
        holder.userName.text = current.name
        holder.userGender.text = current.gender
        holder.userBirth.text = current.birth
    }

    internal fun setUsers(users: List<UserEntity>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun getItemCount() = users.size

}
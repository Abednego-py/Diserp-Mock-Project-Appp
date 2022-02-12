package com.example.mockprojectapp.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mockprojectapp.R

class ProjectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val avatar: ImageView = view.findViewById(R.id.img_profile)
    val login: TextView = view.findViewById(R.id.txt_login)
}
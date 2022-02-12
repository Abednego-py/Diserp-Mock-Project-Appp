package com.example.mockprojectapp.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mockprojectapp.R
import com.example.mockprojectapp.db.ProjectEntity

class FavouritesAdapter(private val items: List<ProjectEntity>, private val block: (Int) -> Unit) :
    RecyclerView.Adapter<ProjectViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemlist, parent, false)
        return ProjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.login.text = items[position].loginName
        holder.avatar.load(items[position].imageUrl) {
            error(R.drawable.ic_baseline_account_box_24)
            placeholder(R.drawable.ic_baseline_account_box_24)
        }
        holder.itemView.setOnClickListener {
            block.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
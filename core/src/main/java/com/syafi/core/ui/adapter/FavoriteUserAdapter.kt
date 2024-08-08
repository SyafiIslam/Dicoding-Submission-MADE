package com.syafi.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syafi.core.data.local.room.entity.FavoriteUser
import com.syafi.core.databinding.GithubUserItemBinding

class FavoriteUserAdapter(
    private val listFavoriteUser: List<FavoriteUser>
): RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {

    private lateinit var onItemClick: OnItemClickCallBack

    interface OnItemClickCallBack {
        fun onItemClick(user: String)
    }

    class ViewHolder(val binding: GithubUserItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= GithubUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFavoriteUser.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text= listFavoriteUser[position].login
        Glide.with(holder.itemView.context)
            .load(listFavoriteUser[position].avatarUrl)
            .into(holder.binding.ivAvatar)

        holder.itemView.setOnClickListener {
            this.onItemClick.onItemClick(listFavoriteUser[holder.adapterPosition].login)
        }
    }

    fun onItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClick= onItemClickCallBack
    }

}
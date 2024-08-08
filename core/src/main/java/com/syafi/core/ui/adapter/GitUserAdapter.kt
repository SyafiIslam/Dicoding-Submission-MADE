package com.syafi.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syafi.core.databinding.GithubUserItemBinding
import com.syafi.core.domain.model.GithubUser

class GitUserAdapter(
    private val listUser: List<GithubUser>
): RecyclerView.Adapter<GitUserAdapter.ViewHolder>() {

    private lateinit var onItemClick: OnItemClickCallBack
    private lateinit var onIconClick: OnIconClickCallBack

    interface OnItemClickCallBack {
        fun onItemClick(user: String)
    }

    interface OnIconClickCallBack {
        fun onIconClick(url: String)
    }

    class ViewHolder(val binding: GithubUserItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= GithubUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text= listUser[position].login
        Glide.with(holder.itemView.context)
            .load(listUser[position].avatar_url)
            .into(holder.binding.ivAvatar)

        holder.itemView.setOnClickListener {
            this.onItemClick.onItemClick(listUser[holder.adapterPosition].login)
        }
        holder.binding.ivOpenInBrowser.setOnClickListener {
            this.onIconClick.onIconClick(listUser[holder.adapterPosition].url)
        }
    }

    fun onItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClick= onItemClickCallBack
    }

    fun onIconClickCallBack(onIconClickCallBack: OnIconClickCallBack) {
        this.onIconClick= onIconClickCallBack
    }
}
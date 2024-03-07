package com.example.submissionakhir.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionakhir.database.Favorite
import com.example.submissionakhir.databinding.ItemUserlistBinding


class AdapterFavorite (private val onItemClick: OnClickListener?): RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {


    private val differ= object: DiffUtil.ItemCallback<Favorite>(){
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem == newItem
        }

    }

    private val dif = AsyncListDiffer(this,differ)
    interface OnClickListener {
        fun itemClick(data: Favorite)
    }

    fun sendCategory(value: List<Favorite>) = dif.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(ItemUserlistBinding.inflate(view,parent,false))
    }

    override fun getItemCount(): Int {
        return dif.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dif.currentList[position]
        data.let { holder.bind(data) }

        holder.itemView.setOnClickListener{
            onItemClick?.itemClick(data)
        }
    }

    inner class ViewHolder(private var binding: ItemUserlistBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Favorite){
            binding.apply {
                Glide.with(this.ivItem)
                    .load(data.avatarUrl)
                    .fitCenter()
                    .into(ivItem)
                tvUsername.text = data.username
            }
        }
    }
}
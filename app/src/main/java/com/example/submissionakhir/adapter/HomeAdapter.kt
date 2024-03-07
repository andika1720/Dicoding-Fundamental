package com.example.submissionakhir.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionakhir.databinding.ItemUserlistBinding
import com.example.submissionakhir.network.response.DataItems


class HomeAdapter (private val onItemClick: OnClickListener?): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val differ= object: DiffUtil.ItemCallback<DataItems>(){
        override fun areItemsTheSame(oldItem: DataItems, newItem: DataItems): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DataItems, newItem: DataItems): Boolean {
            return oldItem == newItem
        }

    }

    private val dif = AsyncListDiffer(this,differ)
    interface OnClickListener {
        fun itemClick(data: DataItems)
    }

    fun sendCategory(value: List<DataItems>) = dif.submitList(value)

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
        fun bind(data: DataItems){
            binding.apply {
                Glide.with(this.ivItem)
                    .load(data.avatarUrl)
                    .fitCenter()
                    .into(ivItem)
                tvUsername.text = data.login
            }
        }
    }
}
package com.example.retrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.databinding.PostListItemLayoutBinding


class PostAdapter(
    private var data: Posts
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    var onItemClick: ((Post) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newData: Posts) {
        this.data = newData
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: PostListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post) {
            binding.lblPostId.text = item.id.toString()
            binding.lblPostTitle.text = item.title
        }
    }
}
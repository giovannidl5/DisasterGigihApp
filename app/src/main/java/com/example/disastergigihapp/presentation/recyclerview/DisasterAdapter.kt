package com.example.disastergigihapp.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.disastergigihapp.data.remote.GeometriesItem
import com.example.disastergigihapp.databinding.ItemPostBinding

class DisasterAdapter(private val reports: List<GeometriesItem>): RecyclerView.Adapter<DisasterAdapter.PostViewHolder>() {
    inner class PostViewHolder(private var binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(disasterReports: GeometriesItem) {
            //show the data in recycler view (item_post.xml)
            val imageUrl = disasterReports.properties?.imageUrl ?: "https://akcdn.detik.net.id/community/pasma/2017/12/05/1512472295871205803.jpg"
            binding.imageBencana.load(imageUrl)
            binding.tvTitleBencana.text = disasterReports.properties?.title ?: disasterReports.properties?.disasterType
            binding.tvWaktuBencana.text = disasterReports.properties?.createdAt
            binding.tvDeskripsiBencana.text = disasterReports.properties?.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int = reports.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(reports[position])
    }
}

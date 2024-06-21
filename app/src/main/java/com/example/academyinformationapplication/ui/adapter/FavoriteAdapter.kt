package com.example.academyinformationapplication.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.academyinformationapplication.data.model.AcademyTeachingProcess
import com.example.academyinformationapplication.databinding.ItemAcademyBinding
import com.example.academyinformationapplication.ui.detail.DetailActivity

class FavoriteAdapter(var academyList: MutableList<AcademyTeachingProcess>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAcademyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(currentAcademy: AcademyTeachingProcess) {
            binding.academyName.text = currentAcademy.academyName // 학원명
            binding.academyAddress.text = currentAcademy.academyAddress // 주소
            binding.teachingProcess.text = currentAcademy.teachingProcess // 교습과정

            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("data", currentAcademy)
                    putExtra("likePosition", adapterPosition)
                    putExtra("myItem", currentAcademy)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAcademyBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(academyList[position])
    }

    override fun getItemCount(): Int = academyList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavorites(newList: List<AcademyTeachingProcess>) {
        academyList.clear()
        academyList.addAll(newList)
        notifyDataSetChanged()
    }
}

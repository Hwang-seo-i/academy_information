package com.example.academyinformationapplication.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.academyinformationapplication.data.model.AcademyData
import com.example.academyinformationapplication.databinding.ItemDetailBinding
import com.example.academyinformationapplication.ui.detail.DetailActivity

class DetailAdapter(
    private var detailList: List<AcademyData>
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentAcademy: AcademyData) {
            binding.fieldCategory.text = currentAcademy.fieldCategory // 분야구분
            binding.teachingProfession.text = currentAcademy.teachingProfession // 교습계열
            binding.teachingCourse.text = currentAcademy.teachingCourse // 교습과목(반)
            binding.teachingPeriod.text = currentAcademy.teachingPeriod // 교습기간
            binding.totalExpense.text = currentAcademy.totalExpense // 기타경비합계

            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("fieldCategory", currentAcademy.fieldCategory)
                    putExtra("teachingProfession", currentAcademy.teachingProfession)
                    putExtra("teachingCourse", currentAcademy.teachingCourse)
                    putExtra("teachingPeriod", currentAcademy.teachingPeriod)
                    putExtra("totalExpense", currentAcademy.totalExpense)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDetailBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(detailList[position])
    }

    override fun getItemCount(): Int = detailList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateDetailList(newDetailList: List<AcademyData>) {
        detailList = newDetailList
        notifyDataSetChanged()
    }
}

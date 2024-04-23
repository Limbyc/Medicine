package com.valance.medicine.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.valance.medicine.R
import com.valance.medicine.data.Doctor

class DoctorAdapter(private val doctors: List<Doctor>) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    inner class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: AppCompatTextView = itemView.findViewById(R.id.name)
        private val professionTextView: AppCompatTextView = itemView.findViewById(R.id.profession)
        private val addInfoTextView: AppCompatTextView = itemView.findViewById(R.id.addInfo)
        private val compasitiveTextView: AppCompatTextView = itemView.findViewById(R.id.compasitive)
        fun bind(doctor: Doctor) {
            nameTextView.text = doctor.name
            professionTextView.text = doctor.profession
            addInfoTextView.text = doctor.addInfo
            compasitiveTextView.text = doctor.compasitive
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.bind(doctor)
    }

    override fun getItemCount(): Int {
        return doctors.size
    }
}

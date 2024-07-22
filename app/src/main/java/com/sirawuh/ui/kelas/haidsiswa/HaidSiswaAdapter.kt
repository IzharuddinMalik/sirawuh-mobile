package com.sirawuh.ui.kelas.haidsiswa

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sirawuh.data.domain.DataHaidSiswaResponse
import com.sirawuh.databinding.ItemHaidSiswaBinding
import java.text.SimpleDateFormat
import java.util.Locale

class HaidSiswaAdapter(private val items: List<DataHaidSiswaResponse>):
    RecyclerView.Adapter<HaidSiswaAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HaidSiswaAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = ItemHaidSiswaBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HaidSiswaAdapter.ViewHolder, position: Int) {
        holder.tvNamaSiswa.text = items[position].namasiswa
        holder.tvTanggalHaid.text = convertDateWithDay(items[position].tanggalhaid!!)
    }

    inner class ViewHolder(binding: ItemHaidSiswaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvNamaSiswa: TextView = binding.tvNamaSiswaHaidItem
        val tvTanggalHaid: TextView = binding.tvTanggalHaidItem
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateWithDay(time: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd")
        val format = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        return format.format(date.parse(time)!!)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
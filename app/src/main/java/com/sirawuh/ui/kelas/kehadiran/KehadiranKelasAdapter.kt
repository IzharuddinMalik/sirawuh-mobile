package com.sirawuh.ui.kelas.kehadiran

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sirawuh.data.domain.DataKehadiranSiswaResponse
import com.sirawuh.databinding.ItemKehadiranKelasBinding
import java.text.SimpleDateFormat
import java.util.Locale

class KehadiranKelasAdapter(private val items: List<DataKehadiranSiswaResponse>):
    RecyclerView.Adapter<KehadiranKelasAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KehadiranKelasAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = ItemKehadiranKelasBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KehadiranKelasAdapter.ViewHolder, position: Int) {
        holder.tvStatusKehadiran.text = items[position].statuskehadiran
        holder.tvTanggalKehadiran.text = convertDateWithDay(items[position].tanggalkehadiran!!)
        Glide.with(context).load(items[position].fotokehadiran).into(holder.ivFotoKehadiran)
    }

    inner class ViewHolder(binding: ItemKehadiranKelasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvStatusKehadiran: TextView = binding.tvStatusKehadiranItem
        val tvTanggalKehadiran: TextView = binding.tvTanggalKehadiranItem
        val ivFotoKehadiran: ImageView = binding.ivFotoKehadiranItem
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

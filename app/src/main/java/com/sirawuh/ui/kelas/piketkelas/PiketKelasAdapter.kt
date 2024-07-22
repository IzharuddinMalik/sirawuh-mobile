package com.sirawuh.ui.kelas.piketkelas

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sirawuh.data.domain.DataPiketSiswaResponse
import com.sirawuh.databinding.ItemPiketKelasBinding
import java.text.SimpleDateFormat
import java.util.Locale

class PiketKelasAdapter(private val items: List<DataPiketSiswaResponse>):
    RecyclerView.Adapter<PiketKelasAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PiketKelasAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = ItemPiketKelasBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PiketKelasAdapter.ViewHolder, position: Int) {
        holder.tvTanggalPiket.text = convertDateWithDay(items[position].tanggalpiket!!)
        Glide.with(context).load(items[position].fotopiket).into(holder.ivFotoPiket)
    }

    inner class ViewHolder(binding: ItemPiketKelasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvTanggalPiket: TextView = binding.tvTanggalPiketItem
        val ivFotoPiket: ImageView = binding.ivFotoPiketItem
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
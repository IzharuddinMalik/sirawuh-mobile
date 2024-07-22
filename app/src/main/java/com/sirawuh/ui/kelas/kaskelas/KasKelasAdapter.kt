package com.sirawuh.ui.kelas.kaskelas

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sirawuh.data.domain.DataKasKelasSiswaResponse
import com.sirawuh.databinding.ItemHaidSiswaBinding
import com.sirawuh.databinding.ItemKasKelasBinding
import java.text.SimpleDateFormat
import java.util.Locale

class KasKelasAdapter(private val items: List<DataKasKelasSiswaResponse>):
    RecyclerView.Adapter<KasKelasAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KasKelasAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = ItemKasKelasBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KasKelasAdapter.ViewHolder, position: Int) {
        holder.tvTanggalBayarKas.text = convertDateWithDay(items[position].tanggalbayarkas!!)
        holder.tvNamaSiswa.text = items[position].namasiswa
    }

    inner class ViewHolder(binding: ItemKasKelasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvNamaSiswa: TextView = binding.tvNamaSiswaKasItem
        val tvTanggalBayarKas: TextView = binding.tvTanggalKasItem
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
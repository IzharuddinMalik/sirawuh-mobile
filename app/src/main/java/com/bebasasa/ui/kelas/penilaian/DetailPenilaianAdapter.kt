package com.bebasasa.ui.kelas.penilaian

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bebasasa.data.domain.DataDetailPenilaianResp
import com.bebasasa.databinding.ItemDetailPenilaianBinding

class DetailPenilaianAdapter(private var items: List<DataDetailPenilaianResp>) :
    RecyclerView.Adapter<DetailPenilaianAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailPenilaianAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDetailPenilaianBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailPenilaianAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.tvNis.text = item.nipnis
        holder.tvNamaLengkap.text = item.namalengkap
        holder.tvNilai.text = item.nilai
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(binding: ItemDetailPenilaianBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvNis: TextView = binding.tvItemDetailNisPenilaian
        val tvNamaLengkap: TextView = binding.tvItemDetailNamaPenilaian
        val tvNilai: TextView = binding.tvItemDetailNilaiPenilaian
    }
}
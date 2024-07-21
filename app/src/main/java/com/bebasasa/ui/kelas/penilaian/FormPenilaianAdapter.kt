package com.bebasasa.ui.kelas.penilaian

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.bebasasa.R
import com.bebasasa.core.callback.ClickCallback
import com.bebasasa.data.domain.DataSiswaResp
import com.bebasasa.databinding.ItemFormTambahPenilaianBinding
import com.google.android.material.textfield.TextInputEditText

class FormPenilaianAdapter(private val items: List<DataSiswaResp>,
    private val listener: ClickCallback<ItemPenilaian>):
    RecyclerView.Adapter<FormPenilaianAdapter.ViewHolder>() {

    private var strNilai = ""

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FormPenilaianAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = ItemFormTambahPenilaianBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FormPenilaianAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.tvNis.text = item.nipnis
        holder.tvNamaSiswa.text = item.namalengkap

        holder.tfNilai.doOnTextChanged { text, _, _, _ ->
            strNilai = text.toString()
        }

        holder.tvTambah.setOnClickListener {
            val itemPenilaian = ItemPenilaian(
                nipnis = item.nipnis,
                nilai = strNilai
            )
            listener.onClick(itemPenilaian)

            holder.llContainerForm.background = AppCompatResources.getDrawable(
                context, R.drawable.bg_outlined_red
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(binding: ItemFormTambahPenilaianBinding): RecyclerView.ViewHolder(binding.root) {
        val tvNis: TextView = binding.itemNisFormPenilaian
        val tvNamaSiswa: TextView = binding.itemNamaSiswaFormPenilaian
        val tfNilai: TextInputEditText = binding.tfNilaiItemSiswa
        val tvTambah: TextView = binding.itemTambahFormPenilaian
        val llContainerForm: LinearLayout = binding.containerFormTambahPenilaian
    }
}
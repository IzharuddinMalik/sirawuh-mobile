package com.bebasasa.ui.kelas.penilaian

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bebasasa.R
import com.bebasasa.core.callback.ClickCallback
import com.bebasasa.data.domain.DataPenilaianResp
import com.bebasasa.databinding.ItemPenilaianKelasBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ListPenilaianKelasAdapter(private val items: List<DataPenilaianResp>,
    private val listener: ClickCallback<DataPenilaianResp>,
    private val listenerHapus: ClickCallback<DataPenilaianResp>) :
    RecyclerView.Adapter<ListPenilaianKelasAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListPenilaianKelasAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = ItemPenilaianKelasBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListPenilaianKelasAdapter.ViewHolder, position: Int) {
        val item = items[position]

        holder.tvJudulPenilaian.text = item.judulpenilaian
        val tanggalConvert = convertDateWithDay(item.tanggalpenilaian)
        holder.tvTanggalPenilaian.text =
            context.getString(R.string.label_tanggal_penilaian, tanggalConvert)

        holder.itemView.setOnClickListener{
            listener.onClick(item)
        }

        holder.llHapusPenilaian.setOnClickListener {
            listenerHapus.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(binding: ItemPenilaianKelasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvJudulPenilaian: TextView = binding.itemJudulPenilaianKelas
        val tvTanggalPenilaian: TextView = binding.itemTanggalPenilaianKelas
        val llHapusPenilaian: LinearLayout = binding.llHapusPenilaian
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateWithDay(time: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd")
        val format = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        return format.format(date.parse(time)!!)
    }
}

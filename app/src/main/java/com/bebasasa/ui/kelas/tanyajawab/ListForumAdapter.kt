package com.bebasasa.ui.kelas.tanyajawab

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bebasasa.R
import com.bebasasa.core.callback.ClickCallback
import com.bebasasa.data.domain.DataListForumResp
import com.bebasasa.databinding.ItemForumTanyaJawabBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ListForumAdapter(private val items: List<DataListForumResp>,
    private val listener: ClickCallback<DataListForumResp>) :
    RecyclerView.Adapter<ListForumAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListForumAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = ItemForumTanyaJawabBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListForumAdapter.ViewHolder, position: Int) {
        val item = items[position]

        holder.tvDateTanyaJawab.text = convertDateWithDay(item.created_date)
        holder.tvJudulTanyaJawab.text = item.judulpertanyaan
        holder.tvNamaLengkapTanyaJawab.text =
            context.getString(R.string.label_dibuatoleh_tanya_jawab, item.namalengkap)
        holder.tvKelasTanyaJawab.text = item.kelas

        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(binding: ItemForumTanyaJawabBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvDateTanyaJawab: TextView = binding.tvItemDateTanyaJawab
        val tvJudulTanyaJawab: TextView = binding.tvItemJudulTanyaJawab
        val tvNamaLengkapTanyaJawab: TextView = binding.tvItemNamaTanyaJawab
        val tvKelasTanyaJawab: TextView = binding.tvItemKelasTanyaJawab
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateWithDay(time: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd")
        val format = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        return format.format(date.parse(time)!!)
    }
}

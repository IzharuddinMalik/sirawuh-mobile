package com.bebasasa.ui.kelas.tanyajawab

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bebasasa.data.domain.DataChatForumResp
import com.bebasasa.databinding.ItemChatDetailForumBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ChatForumAdapter(private val items: List<DataChatForumResp>) :
    RecyclerView.Adapter<ChatForumAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatForumAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChatDetailForumBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatForumAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.tvNamaLengkap.text = item.namalengkap
        holder.tvMessageChat.text = item.message
        holder.tvKelas.text = item.kelas
        holder.tvDate.text = convertDateWithDay(item.created_date)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(binding: ItemChatDetailForumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvNamaLengkap: TextView = binding.tvItemNamaChatForum
        val tvMessageChat: TextView = binding.tvItemPesanChatForum
        val tvKelas: TextView = binding.tvItemKelasChatForum
        val tvDate: TextView = binding.tvItemDateChatForum
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateWithDay(time: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd h:m:s")
        val format = SimpleDateFormat("h:m dd MMMM yyyy", Locale("id", "ID"))
        return format.format(date.parse(time)!!)
    }
}

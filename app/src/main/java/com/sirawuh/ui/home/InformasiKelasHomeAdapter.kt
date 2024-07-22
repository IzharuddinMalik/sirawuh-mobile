package com.sirawuh.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.sirawuh.core.callback.ClickCallback
import com.bumptech.glide.Glide
import com.sirawuh.data.domain.DataPengumumanResponse
import com.sirawuh.databinding.ItemPapanInformasiHomeBinding
import java.text.SimpleDateFormat
import java.util.Locale

class InformasiKelasHomeAdapter(private val items: List<DataPengumumanResponse>,
    private val listenerDetail: ClickCallback<DataPengumumanResponse>
) :
    RecyclerView.Adapter<InformasiKelasHomeAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = ItemPapanInformasiHomeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvJudulInformasi.text = item.judulpengumuman
        holder.tvDateInformasi.text = item.created_date?.let { convertDateWithDay(it) }
        holder.tvDescInformasi.text = item.isipengumuman

        when (item.mediaextensi) {
            ".png" -> {
                Glide.with(context).load(item.filepengumuman).into(holder.ivInformasi)
                holder.vidInformasi.visibility = View.GONE
                holder.ivInformasi.visibility = View.VISIBLE
            }

            ".jpeg" -> {
                Glide.with(context).load(item.filepengumuman).into(holder.ivInformasi)
                holder.vidInformasi.visibility = View.GONE
                holder.ivInformasi.visibility = View.VISIBLE
            }

            ".jpg" -> {
                Glide.with(context).load(item.filepengumuman).into(holder.ivInformasi)
                holder.vidInformasi.visibility = View.GONE
                holder.ivInformasi.visibility = View.VISIBLE
            }

            ".mp4" -> {
                val uriParse = Uri.parse(item.filepengumuman)
                val mediaController = MediaController(context)
                mediaController.setAnchorView(holder.vidInformasi)
                holder.vidInformasi.setMediaController(mediaController)
                holder.vidInformasi.setVideoURI(uriParse)
                holder.vidInformasi.requestFocus()
                holder.vidInformasi.pause()

                holder.vidInformasi.visibility = View.VISIBLE
                holder.ivInformasi.visibility = View.GONE
            }

            ".mov" -> {
                val uriParse = Uri.parse(item.filepengumuman)
                val mediaController = MediaController(context)
                mediaController.setAnchorView(holder.vidInformasi)
                holder.vidInformasi.setMediaController(mediaController)
                holder.vidInformasi.setVideoURI(uriParse)
                holder.vidInformasi.requestFocus()
                holder.vidInformasi.pause()

                holder.vidInformasi.visibility = View.VISIBLE
                holder.ivInformasi.visibility = View.GONE
            }

            ".mpeg4" -> {
                val uriParse = Uri.parse(item.filepengumuman)
                val mediaController = MediaController(context)
                mediaController.setAnchorView(holder.vidInformasi)
                holder.vidInformasi.setMediaController(mediaController)
                holder.vidInformasi.setVideoURI(uriParse)
                holder.vidInformasi.requestFocus()
                holder.vidInformasi.pause()

                holder.vidInformasi.visibility = View.VISIBLE
                holder.ivInformasi.visibility = View.GONE
            }
        }

        holder.tvDetailInformasi.setOnClickListener {
            listenerDetail.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(binding: ItemPapanInformasiHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvJudulInformasi: TextView = binding.tvTitlePapanInformasiHome
        val tvDateInformasi: TextView = binding.tvDatePapanInformasiHome
        val tvDescInformasi: TextView = binding.tvDescPapanInformasiHome
        val tvDetailInformasi: TextView = binding.tvDetailPapanInformasiHome
        val ivInformasi: ImageView = binding.ivItemImagePapanInformasiHome
        val vidInformasi: VideoView = binding.vidItemPapanInformasiHome
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateWithDay(time: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd")
        val format = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        return format.format(date.parse(time)!!)
    }
}
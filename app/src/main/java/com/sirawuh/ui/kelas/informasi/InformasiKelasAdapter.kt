package com.sirawuh.ui.kelas.informasi

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sirawuh.core.callback.ClickCallback
import com.sirawuh.data.domain.DataPengumumanResponse
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.databinding.PapanInformasiSectionBinding
import java.text.SimpleDateFormat
import java.util.Locale


class InformasiKelasAdapter(
    private val items: List<DataPengumumanResponse>,
    private val listenerDetail: ClickCallback<DataPengumumanResponse>,
    private val listenerEdit: ClickCallback<DataPengumumanResponse>,
    private val listenerHapus: ClickCallback<DataPengumumanResponse>,
    private val preferenceHelper: PreferenceHelper
) :
    RecyclerView.Adapter<InformasiKelasAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = PapanInformasiSectionBinding.inflate(inflater, parent, false)
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

        holder.llHapusInformasi.setOnClickListener {
            listenerHapus.onClick(item)
        }

        holder.llEditInformasi.setOnClickListener {
            listenerEdit.onClick(item)
        }

        when(preferenceHelper.getNipNisSession()) {
            "15" -> {
                holder.llHapusInformasi.visibility = View.VISIBLE
                holder.llEditInformasi.visibility = View.VISIBLE
            } else -> {
                holder.llHapusInformasi.visibility = View.GONE
                holder.llEditInformasi.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(binding: PapanInformasiSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvJudulInformasi: TextView = binding.tvTitlePapanInformasi
        val tvDateInformasi: TextView = binding.tvDatePapanInformasi
        val tvDescInformasi: TextView = binding.tvDescPapanInformasi
        val tvDetailInformasi: TextView = binding.tvDetailPapanInformasi
        val ivInformasi: ImageView = binding.ivItemImagePapanInformasi
        val vidInformasi: VideoView = binding.vidItemPapanInformasi
        val llEditInformasi: LinearLayout = binding.llEditPapanInformasi
        val llHapusInformasi: LinearLayout = binding.llHapusPapanInformasi
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateWithDay(time: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd")
        val format = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        return format.format(date.parse(time)!!)
    }
}

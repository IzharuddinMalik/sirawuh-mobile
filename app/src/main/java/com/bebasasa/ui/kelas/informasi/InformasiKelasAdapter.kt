package com.bebasasa.ui.kelas.informasi

import android.annotation.SuppressLint
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
import com.bebasasa.core.callback.ClickCallback
import com.bebasasa.data.domain.ItemInformasiKelasResp
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.databinding.PapanInformasiSectionBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class InformasiKelasAdapter(
    private val items: List<ItemInformasiKelasResp>,
    private val listenerDetail: ClickCallback<ItemInformasiKelasResp>,
    private val listenerEdit: ClickCallback<ItemInformasiKelasResp>,
    private val listenerHapus: ClickCallback<ItemInformasiKelasResp>,
    private val preferenceHelper: PreferenceHelper
) :
    RecyclerView.Adapter<InformasiKelasAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InformasiKelasAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = PapanInformasiSectionBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InformasiKelasAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.tvJudulInformasi.text = item.judulinformasi
        holder.tvDateInformasi.text = convertDateWithDay(item.created_date)
        holder.tvDescInformasi.text = item.deskripsiinformasi

        when (item.mediaextensi) {
            ".png" -> {
                Glide.with(context).load(item.mediainformasi).into(holder.ivInformasi)
                holder.vidInformasi.visibility = View.GONE
                holder.ivInformasi.visibility = View.VISIBLE
            }

            ".jpeg" -> {
                Glide.with(context).load(item.mediainformasi).into(holder.ivInformasi)
                holder.vidInformasi.visibility = View.GONE
                holder.ivInformasi.visibility = View.VISIBLE
            }

            ".jpg" -> {
                Glide.with(context).load(item.mediainformasi).into(holder.ivInformasi)
                holder.vidInformasi.visibility = View.GONE
                holder.ivInformasi.visibility = View.VISIBLE
            }

            ".mp4" -> {
                val uriParse = Uri.parse(item.mediainformasi)
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
                val uriParse = Uri.parse(item.mediainformasi)
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
                val uriParse = Uri.parse(item.mediainformasi)
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

        when(preferenceHelper.getTipeUserSession()) {
            "1" -> {
                holder.llHapusInformasi.visibility = View.GONE
                holder.llEditInformasi.visibility = View.GONE
            }

            "2" -> {
                holder.llHapusInformasi.visibility = View.VISIBLE
                holder.llEditInformasi.visibility = View.VISIBLE
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

package com.bebasasa.core.customview

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.MediaController
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.bebasasa.data.domain.ItemInformasiKelasResp
import com.bebasasa.databinding.LayouDetailPapanInformasiBinding
import com.bebasasa.databinding.LayoutDeleteBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

object CustomDialog {

    fun showPopUpConfirmation(
        context: Context,
        layoutInflater: LayoutInflater,
        textComponent: ComponentDialog,
        onConfirmed: (() -> Unit)? = null,
        onRefused: (() -> Unit)? = null,
    ) {
        kotlin.runCatching {
            val dialog = Dialog(context)
            val binding = LayoutDeleteBinding.inflate(layoutInflater)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(binding.root)
            dialog.setCancelable(true)
            binding.textView6.text = textComponent.title
            binding.tvTitleWording.text = textComponent.message

            binding.btnTidak.isVisible = textComponent.buttonLeft.isNotEmpty()
            binding.btnTidak.text = textComponent.buttonLeft
            binding.btnTidak.setOnClickListener {
                onRefused?.invoke()
                dialog.dismiss()
            }

            binding.btnYa.isVisible = textComponent.buttonRight.isNotEmpty()
            binding.btnYa.text = textComponent.buttonRight
            binding.btnYa.setOnClickListener {
                onConfirmed?.invoke()
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    fun showPopupDetailInformasi(
        context: Context,
        layoutInflater: LayoutInflater,
        item: ItemInformasiKelasResp
    ) {
        kotlin.runCatching {
            val dialog = Dialog(context)
            val binding = LayouDetailPapanInformasiBinding.inflate(layoutInflater)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(binding.root)
            dialog.setCancelable(true)
            binding.tvTitlePapanInformasiDetail.text = item.judulinformasi
            binding.tvDatePapanInformasiDetail.text = convertDateWithDay(item.created_date)
            binding.tvDescPapanInformasiDetail.text = item.deskripsiinformasi

            when (item.mediaextensi) {
                ".png" -> {
                    Glide.with(context).load(item.mediainformasi).into(binding.ivItemImagePapanInformasiDetail)
                    binding.vidItemPapanInformasiDetail.visibility = View.GONE
                    binding.ivItemImagePapanInformasiDetail.visibility = View.VISIBLE
                }

                ".jpeg" -> {
                    Glide.with(context).load(item.mediainformasi).into(binding.ivItemImagePapanInformasiDetail)
                    binding.vidItemPapanInformasiDetail.visibility = View.GONE
                    binding.ivItemImagePapanInformasiDetail.visibility = View.VISIBLE
                }

                ".jpg" -> {
                    Glide.with(context).load(item.mediainformasi).into(binding.ivItemImagePapanInformasiDetail)
                    binding.vidItemPapanInformasiDetail.visibility = View.GONE
                    binding.ivItemImagePapanInformasiDetail.visibility = View.VISIBLE
                }

                ".mp4" -> {
                    val uriParse = Uri.parse(item.mediainformasi)
                    val mediaController = MediaController(context)
                    mediaController.setAnchorView(binding.vidItemPapanInformasiDetail)
                    binding.vidItemPapanInformasiDetail.setMediaController(mediaController)
                    binding.vidItemPapanInformasiDetail.setVideoURI(uriParse)
                    binding.vidItemPapanInformasiDetail.requestFocus()
                    binding.vidItemPapanInformasiDetail.pause()

                    binding.vidItemPapanInformasiDetail.visibility = View.VISIBLE
                    binding.ivItemImagePapanInformasiDetail.visibility = View.GONE
                }

                ".mov" -> {
                    val uriParse = Uri.parse(item.mediainformasi)
                    val mediaController = MediaController(context)
                    mediaController.setAnchorView(binding.vidItemPapanInformasiDetail)
                    binding.vidItemPapanInformasiDetail.setMediaController(mediaController)
                    binding.vidItemPapanInformasiDetail.setVideoURI(uriParse)
                    binding.vidItemPapanInformasiDetail.requestFocus()
                    binding.vidItemPapanInformasiDetail.pause()

                    binding.vidItemPapanInformasiDetail.visibility = View.VISIBLE
                    binding.ivItemImagePapanInformasiDetail.visibility = View.GONE
                }

                ".mpeg4" -> {
                    val uriParse = Uri.parse(item.mediainformasi)
                    val mediaController = MediaController(context)
                    mediaController.setAnchorView(binding.vidItemPapanInformasiDetail)
                    binding.vidItemPapanInformasiDetail.setMediaController(mediaController)
                    binding.vidItemPapanInformasiDetail.setVideoURI(uriParse)
                    binding.vidItemPapanInformasiDetail.requestFocus()
                    binding.vidItemPapanInformasiDetail.pause()

                    binding.vidItemPapanInformasiDetail.visibility = View.VISIBLE
                    binding.ivItemImagePapanInformasiDetail.visibility = View.GONE
                }
            }
            dialog.show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateWithDay(time: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd")
        val format = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        return format.format(date.parse(time)!!)
    }

    data class ComponentDialog(
        var title: String = "",
        var message: String = "",
        var buttonRight: String = "",
        var buttonLeft: String = "",
        @DrawableRes var drawableRes: Int? = null,
    )
}
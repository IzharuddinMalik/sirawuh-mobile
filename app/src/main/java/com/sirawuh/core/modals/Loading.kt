package com.sirawuh.core.modals

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.sirawuh.R
import com.sirawuh.databinding.LoadingBinding

class LoadingDialog(layoutInflater: LayoutInflater, context: Context) {
    private val layoutInflater: LayoutInflater
    private val context: Context

    init {
        this.layoutInflater = layoutInflater
        this.context = context
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun start() {
        val builder = AlertDialog.Builder(context)
        val binding: LoadingBinding = LoadingBinding.inflate(layoutInflater)
        builder.setView(binding.root)
        builder.setCancelable(false)
        binding.progressBar
            .indeterminateDrawable
            .colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            context.getColor(R.color.primary),
            BlendModeCompat.SRC_ATOP
        )
        dialog = builder.create()
        dialog?.show()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    fun dismiss() {
        dialog?.dismiss()
    }

    companion object {
        private var dialog: AlertDialog? = null
    }
}
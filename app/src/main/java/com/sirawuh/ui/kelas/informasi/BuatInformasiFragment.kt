package com.sirawuh.ui.kelas.informasi

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.sirawuh.core.base.BaseFragment
import com.sirawuh.utils.RealPathUtils
import com.sirawuh.utils.navigateInclusivelyToUri
import com.sirawuh.utils.observe
import com.google.android.material.snackbar.Snackbar
import com.sirawuh.R
import com.sirawuh.databinding.FragmentBuatInformasiBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class BuatInformasiFragment :
    BaseFragment<FragmentBuatInformasiBinding>(FragmentBuatInformasiBinding::inflate) {

    private val viewModel: InformasiKelasViewModel by viewModels()
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreated() {
        viewModel.initState()
        mProgressDialog = ProgressDialog(requireActivity())
        binding.apply {
            toolbarBuatInformasi.tvTitle.text = getString(R.string.label_informasi_kelas)
            toolbarBuatInformasi.ivBack.setOnClickListener {
                navigateInclusivelyToUri(R.string.informasi_kelas_route)
            }
        }

        checkPermission(
            Manifest.permission.READ_MEDIA_VIDEO,
            externalStorage
        )

        checkPermission(
            Manifest.permission.READ_MEDIA_IMAGES,
            externalStorage
        )

        initComponent()
        setupListener()
        observer()
    }

    private fun initComponent() {
        binding.tfJudulInformasi.doOnTextChanged { text, _, _, _ ->
            viewModel.StateHandler().setJudulInformasi(text.toString())
        }

        binding.tfDeskripsiInformasi.doOnTextChanged { text, _, _, _ ->
            viewModel.StateHandler().setDeskripsiInformasi(text.toString())
        }
    }

    private fun setupListener() {
        binding.tvUploadMediaInformasi.setOnClickListener {
            openGallery()
        }

        binding.btnTambahInformasi.setOnClickListener {
            viewModel.RequestHandler().sendBuatInformasi()
        }
    }

    private fun observer() {
        observe(viewModel.transparentLoading) {
            if (it) {
                mProgressDialog?.setTitle("Sedang menambahkan data")
                mProgressDialog?.setMessage("Harap menunggu sebentar...")
                mProgressDialog?.show()
            } else {
                mProgressDialog?.dismiss()
            }
        }

        observe(viewModel.judulInformasiValid) {
            val message = if (it == false) getString(R.string.field_empty) else ""
            if (message != "") Snackbar.make(
                binding.containerBuatInformasi,
                message,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        observe(viewModel.deskripsiInformasiValid) {
            val message = if (it == false) getString(R.string.field_empty) else ""
            if (message != "") Snackbar.make(
                binding.containerBuatInformasi,
                message,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        observe(viewModel.isButtonEnable) {
            if (it != null) {
                binding.btnTambahInformasi.isEnabled = it
                if (it) {
                    binding.btnTambahInformasi.background = AppCompatResources.getDrawable(
                        requireActivity(), R.drawable.bg_bebasasa_button
                    ) ?: AppCompatResources.getDrawable(
                        requireActivity(), R.drawable.button_disable
                    )
                }
            }
        }

        observe(viewModel.buatInformasiResp) {
            if (it != null) {
                if (it.success == 1) {
                    Snackbar.make(binding.containerBuatInformasi, it.message, Snackbar.LENGTH_SHORT)
                        .show()
                    navigateInclusivelyToUri(R.string.informasi_kelas_route)
                } else {
                    Snackbar.make(binding.containerBuatInformasi, it.message, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }

        observe(viewModel.error) {
            if (it != "") Snackbar.make(binding.containerBuatInformasi, it, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {

            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == externalStorage) {
            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                //permission from popup granted
                openGallery()
            } else {
                //permission from popup denied
                Toast.makeText(requireActivity(), "Permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun openGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "*/*"
        startActivityForResult(intent, mediaPickCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == mediaPickCode) {
            mediaUri = data?.data!!
            val realPathUtils = RealPathUtils()
            val absolutePath = realPathUtils.getPath(requireContext(), mediaUri!!)
                .toString().removePrefix("/storage/emulated/0/")
            binding.tvUploadMediaInformasi.text = absolutePath
            val mediaFile = File(realPathUtils.getPath(requireActivity(), mediaUri!!).toString())
            viewModel.StateHandler().mediaInformasi(mediaFile, absolutePath)
        }

    }

    companion object {
        var mediaUri: Uri? = null
        //Permission code
        private const val externalStorage = 100
        private const val mediaPickCode = 1002
    }
}

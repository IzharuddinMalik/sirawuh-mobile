package com.bebasasa.ui.kelas.informasi

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.bebasasa.R
import com.bebasasa.core.base.BaseFragment
import com.bebasasa.databinding.FragmentUbahInformasiBinding
import com.bebasasa.utils.RealPathUtils
import com.bebasasa.utils.navigateInclusivelyToUri
import com.bebasasa.utils.observe
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class UbahInformasiFragment :
    BaseFragment<FragmentUbahInformasiBinding>(FragmentUbahInformasiBinding::inflate) {

    private val viewModel: InformasiKelasViewModel by viewModels()

    override fun onCreated() {
        viewModel.initState()
        initComponent()
        initSetupListener()
        observer()
    }

    private fun initComponent() {
        idInformasi = arguments?.getString("idinformasi").orEmpty()
        judulInformasi = arguments?.getString("judulinformasi").orEmpty()
        deskripsiInformasi = arguments?.getString("deskripsi").orEmpty()

        binding.apply {
            tfJudulInformasi.setText(judulInformasi)
            tfDeskripsiInformasi.setText(deskripsiInformasi)

            viewModel.StateHandler().setIdPapanInformasi(idInformasi)
            viewModel.StateHandler().setJudulInformasi(judulInformasi)
            viewModel.StateHandler().setDeskripsiInformasi(deskripsiInformasi)

            tfJudulInformasi.doOnTextChanged { text, _, _, _ ->
                viewModel.StateHandler().setJudulInformasi(text.toString())
            }

            tfDeskripsiInformasi.doOnTextChanged { text, _, _, _ ->
                viewModel.StateHandler().setDeskripsiInformasi(text.toString())
            }

            toolbarUbahInformasi.ivBack.setOnClickListener {
                navigateInclusivelyToUri(R.string.informasi_kelas_route)
            }

            toolbarUbahInformasi.tvTitle.text = getString(R.string.title_ubah_informasi)
        }

        checkPermission(
            Manifest.permission.READ_MEDIA_VIDEO,
            externalStorage
        )

        checkPermission(
            Manifest.permission.READ_MEDIA_IMAGES,
            externalStorage
        )
    }

    private fun initSetupListener() {
        binding.tvUploadMediaInformasi.setOnClickListener {
            openGallery()
        }

        binding.btnUbahInformasi.setOnClickListener {
            viewModel.RequestHandler().ubahInformasi()
        }
    }

    private fun observer() {
        observe(viewModel.judulInformasiValid) {
            val message = if (it == false) getString(R.string.field_empty) else ""
            if (message != "") Snackbar.make(
                binding.containerUbahInformasi,
                message,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        observe(viewModel.deskripsiInformasiValid) {
            val message = if (it == false) getString(R.string.field_empty) else ""
            if (message != "") Snackbar.make(
                binding.containerUbahInformasi,
                message,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        observe(viewModel.isButtonEnable) {
            if (it != null) {
                binding.btnUbahInformasi.isEnabled = it
                if (it) {
                    binding.btnUbahInformasi.background = AppCompatResources.getDrawable(
                        requireActivity(), R.drawable.bg_bebasasa_button
                    ) ?: AppCompatResources.getDrawable(
                        requireActivity(), R.drawable.button_disable
                    )
                }
            }
        }

        observe(viewModel.ubahInformasiResp) {
            if (it != null) {
                if (it.success == 1) {
                    Snackbar.make(binding.containerUbahInformasi, it.message, Snackbar.LENGTH_SHORT)
                        .show()
                    navigateInclusivelyToUri(R.string.informasi_kelas_route)
                } else {
                    Snackbar.make(binding.containerUbahInformasi, it.message, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }

        observe(viewModel.error) {
            if (it != "") Snackbar.make(binding.containerUbahInformasi, it, Snackbar.LENGTH_SHORT)
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
        private var idInformasi = ""
        private var judulInformasi = ""
        private var deskripsiInformasi = ""

        var mediaUri: Uri? = null

        //Permission code
        private const val externalStorage = 100
        private const val mediaPickCode = 1002
    }
}

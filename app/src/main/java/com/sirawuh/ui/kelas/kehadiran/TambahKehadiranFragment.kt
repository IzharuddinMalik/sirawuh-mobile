package com.sirawuh.ui.kelas.kehadiran

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.sirawuh.R
import com.sirawuh.core.base.BaseFragment
import com.sirawuh.core.customview.CustomDialog
import com.sirawuh.databinding.FragmentTambahKehadiranBinding
import com.sirawuh.utils.RealPathUtils
import com.sirawuh.utils.navigateToUri
import com.sirawuh.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class TambahKehadiranFragment:
    BaseFragment<FragmentTambahKehadiranBinding>(FragmentTambahKehadiranBinding::inflate) {

    private val viewModel: KehadiranViewModel by viewModels()
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreated() {
        viewModel.initState()
        mProgressDialog = ProgressDialog(requireActivity())

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
        binding.apply {
            toolbarTambahKehadiran.ivBack.setOnClickListener{
                navigateToUri(R.string.kehadiran_kelas_route)
            }

            toolbarTambahKehadiran.tvTitle.text = getString(R.string.title_tambah_kehadiran)
        }

        binding.tvUploadBuktiKehadiran.setOnClickListener {
            openGallery()
        }

        viewModel.RequestHandler().getStatusKehadiran()
    }

    private fun setupListener() {
        binding.btnTambahKehadiranSiswa.setOnClickListener {
            viewModel.RequestHandler().sendTambahKehadiran()
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

        observe(viewModel.listStatusKehadiran) {
            if (it != null) {
                var idStatusKehadiran = arrayOfNulls<String>(it.size + 1)
                var statusKehadiran = arrayOfNulls<String>(it.size + 1)

                idStatusKehadiran[0] = "0"
                statusKehadiran[0] = "Pilih Status Kehadiran"

                for (i in 0 until it.size) {
                    var data = it[i]

                    idStatusKehadiran[i+1] = data.idstatuskehadiran
                    statusKehadiran[i+1] = data.statuskehadiran
                }

                var adapter = this.let { ArrayAdapter(requireActivity(), R.layout.inflater_textview, statusKehadiran) }
                adapter.setDropDownViewResource(R.layout.inflater_textview)
                binding.spStatusKehadiran.setAdapter(adapter)

                binding.spStatusKehadiran.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        positions: Int,
                        id: Long
                    ) {
                        viewModel.StateHandler().setStatusKehadiran(idStatusKehadiran[positions].toString())
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }

            }
        }

        observe(viewModel.statusKehadiranValid) {
            val message = if (it == false) getString(R.string.field_empty) else ""
            if (message != "") Snackbar.make(
                binding.containerTambahKehadiran,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }

        observe(viewModel.fotoKehadiranValid) {
            val message = if (it == false) getString(R.string.field_empty) else ""
            if (message != "") Snackbar.make(
                binding.containerTambahKehadiran,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }

        observe(viewModel.isButtonEnable) {
            if (it != null) {
                binding.btnTambahKehadiranSiswa.isEnabled = it
                if (it) {
                    binding.btnTambahKehadiranSiswa.background = AppCompatResources.getDrawable(
                        requireActivity(), R.drawable.bg_bebasasa_button
                    ) ?: AppCompatResources.getDrawable(
                        requireActivity(), R.drawable.button_disable
                    )
                }
            }
        }

        observe(viewModel.tambahKehadiranResp) {
            if (it != null) {
                if (it.success == 1) {
                    navigateToUri(R.string.kehadiran_kelas_route)
                } else {
                    CustomDialog.showErrorMessage(requireActivity(), layoutInflater, getString(R.string.label_title_error_added), it.message)
                }
            }
        }

        observe(viewModel.error) {
            if (it != "") Snackbar.make(binding.containerTambahKehadiran, it, Toast.LENGTH_LONG).show()
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
            binding.tvUploadBuktiKehadiran.text = absolutePath
            val mediaFile = File(realPathUtils.getPath(requireActivity(), mediaUri!!).toString())
            viewModel.StateHandler().setFotoBukti(mediaFile, absolutePath)
        }

    }

    companion object {
        var mediaUri: Uri? = null
        //Permission code
        private const val externalStorage = 100
        private const val mediaPickCode = 1002
    }
}

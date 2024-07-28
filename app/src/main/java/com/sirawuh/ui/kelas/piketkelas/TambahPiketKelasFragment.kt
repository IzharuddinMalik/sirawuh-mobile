package com.sirawuh.ui.kelas.piketkelas

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
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.sirawuh.R
import com.sirawuh.core.base.BaseFragment
import com.sirawuh.core.customview.CustomDialog
import com.sirawuh.databinding.FragmentTambahPiketKelasBinding
import com.sirawuh.ui.kelas.kehadiran.TambahKehadiranFragment
import com.sirawuh.utils.RealPathUtils
import com.sirawuh.utils.navigateInclusivelyToUri
import com.sirawuh.utils.navigateToUri
import com.sirawuh.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class TambahPiketKelasFragment:
    BaseFragment<FragmentTambahPiketKelasBinding>(FragmentTambahPiketKelasBinding::inflate) {

    private val viewModel: PiketKelasViewModel by viewModels()
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
            toolbarTambahPiketKelas.ivBack.setOnClickListener {
                navigateToUri(R.string.piket_kelas_route)
            }

            toolbarTambahPiketKelas.tvTitle.text = getString(R.string.title_tambah_piketkelas)
        }
    }

    private fun setupListener() {
        binding.btnTambahPiketKelasSiswa.setOnClickListener {
            viewModel.RequestHandler().sendPiket()
        }

        binding.tvPilihTanggalPiket.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Pilih Tanggal Piket")
                    .build()

            datePicker.show(requireActivity().supportFragmentManager, "tag")

            datePicker.addOnPositiveButtonClickListener {
                viewModel.StateHandler().convertDate(it)
            }
            datePicker.addOnCancelListener {
                datePicker.dismiss()
            }
            datePicker.addOnDismissListener {
                datePicker.dismiss()
            }
        }

        binding.tvUploadBuktiPiket.setOnClickListener {
            openGallery()
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

        observe(viewModel.tanggalPiketConvert) {
            if (it != "") {
                binding.tvPilihTanggalPiket.text = it
            } else {
                binding.tvPilihTanggalPiket.text = getString(R.string.title_pilih_tanggal_piketkelas)
            }
        }

        observe(viewModel.isButtonEnable) {
            if (it != null) {
                binding.btnTambahPiketKelasSiswa.isEnabled = it
                if (it) {
                    binding.btnTambahPiketKelasSiswa.background = AppCompatResources.getDrawable(
                        requireActivity(), R.drawable.bg_bebasasa_button
                    ) ?: AppCompatResources.getDrawable(
                        requireActivity(), R.drawable.button_disable
                    )
                }
            }
        }

        observe(viewModel.tambahPiketResp) {
            if (it != null) {
                if (it.success == 1) {
                    navigateInclusivelyToUri(R.string.piket_kelas_route)
                } else {
                    CustomDialog.showErrorMessage(requireActivity(), layoutInflater, getString(R.string.label_title_error_added), it.message)
                }
            }
        }

        observe(viewModel.error) {
            if (it != "") Snackbar.make(binding.containerTambahPiketKelas, it, Toast.LENGTH_SHORT).show()
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
            binding.tvUploadBuktiPiket.text = absolutePath
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

package com.sirawuh.ui.kelas.kaskelas

import android.app.ProgressDialog
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.sirawuh.R
import com.sirawuh.core.base.BaseFragment
import com.sirawuh.core.customview.CustomDialog
import com.sirawuh.databinding.FragmentTambahBayarKasBinding
import com.sirawuh.utils.navigateInclusivelyToUri
import com.sirawuh.utils.navigateToUri
import com.sirawuh.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TambahKasKelasFragment:
    BaseFragment<FragmentTambahBayarKasBinding>(FragmentTambahBayarKasBinding::inflate) {

    private val viewModel: KasKelasViewModel by viewModels()
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreated() {
        mProgressDialog = ProgressDialog(requireActivity())
        initComponent()
        setupListener()
        observer()
    }

    private fun initComponent() {
        binding.apply {
            toolbarTambahKasKelas.ivBack.setOnClickListener {
                navigateToUri(R.string.kas_kelas_route)
            }

            toolbarTambahKasKelas.tvTitle.text = getString(R.string.title_tambah_piketkelas)
        }
    }

    private fun setupListener() {
        binding.btnTambahKasKelas.setOnClickListener {
            viewModel.RequestHandler().sendBayarKas()
        }

        binding.tvPilihTanggalBayarKas.setOnClickListener {
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

        observe(viewModel.tanggalBayarKasConvert) {
            if (it != "") {
                binding.tvPilihTanggalBayarKas.text = it
            } else {
                binding.tvPilihTanggalBayarKas.text = getString(R.string.title_pilih_tanggal_piketkelas)
            }
        }

        observe(viewModel.tambahKasKelasResp) {
            if (it != null) {
                if (it.success == 1) {
                    navigateInclusivelyToUri(R.string.kas_kelas_route)
                } else {
                    CustomDialog.showErrorMessage(requireActivity(), layoutInflater, getString(R.string.label_title_error_added), it.message)
                }
            }
        }

        observe(viewModel.error) {
            if (it != "") Snackbar.make(binding.containerTambahKasKelas, it, Toast.LENGTH_LONG).show()
        }
    }
}

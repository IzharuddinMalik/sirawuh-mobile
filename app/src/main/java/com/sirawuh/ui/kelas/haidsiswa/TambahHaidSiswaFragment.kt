package com.sirawuh.ui.kelas.haidsiswa

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.sirawuh.R
import com.sirawuh.core.base.BaseFragment
import com.sirawuh.databinding.FragmentTambahHaidsiswaBinding
import com.sirawuh.utils.navigateInclusivelyToUri
import com.sirawuh.utils.navigateToUri
import com.sirawuh.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TambahHaidSiswaFragment:
    BaseFragment<FragmentTambahHaidsiswaBinding>(FragmentTambahHaidsiswaBinding::inflate) {

    private val viewModel: HaidSiswaViewModel by viewModels()

    override fun onCreated() {
        viewModel.initState()

        initComponent()
        setupListener()
        observer()
    }

    private fun initComponent() {
        binding.toolbarTambahInfoHaid.ivBack.setOnClickListener {
            navigateToUri(R.string.haid_kelas_route)
        }

        binding.toolbarTambahInfoHaid.tvTitle.text = getString(R.string.title_tambah_haidsiswa)
    }

    private fun setupListener() {
        binding.btnTambahInfoHaidSiswa.setOnClickListener {
            viewModel.RequestHandler().sendHaidSiswa()
        }

        binding.tvPilihTanggalHaid.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Pilih Tanggal Mulai Haid")
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
        observe(viewModel.tanggalHaidConvert) {
            if (it != "") {
                binding.tvPilihTanggalHaid.text = it
            } else {
                binding.tvPilihTanggalHaid.text = getString(R.string.title_pilih_tanggal_haid)
            }
        }

        observe(viewModel.tambahHaidResp) {
            if (it != null) {
                if (it.success == 1) {
                    navigateInclusivelyToUri(R.string.haid_kelas_route)
                }
            }
        }

        observe(viewModel.error) {
            if (it != "") Snackbar.make(binding.containerTambahInfoHaid, it, Toast.LENGTH_LONG).show()
        }
    }
}
package com.sirawuh.ui.kelas.kehadiran

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sirawuh.R
import com.sirawuh.core.base.BaseFragment
import com.sirawuh.databinding.FragmentKehadiranKelasBinding
import com.sirawuh.utils.navigateToUri
import com.sirawuh.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KehadiranKelasFragment:
    BaseFragment<FragmentKehadiranKelasBinding>(FragmentKehadiranKelasBinding::inflate) {

    private val viewModel: KehadiranViewModel by viewModels()

    override fun onCreated() {
        viewModel.RequestHandler().getListKehadiran()

        observer()
        setupListener()
    }

    private fun setupListener() {
        binding.toolbarKehadiranSiswa.ivBack.setOnClickListener {
            navigateToUri(
                getString(R.string.home_route).replace(
                    "{destination}",
                    getString(R.string.label_kelas)
                )
            )
        }

        binding.toolbarKehadiranSiswa.tvTitle.text = getString(R.string.label_kehadiran_kelas)

        binding.btnTambahKehadiran.setOnClickListener {
            navigateToUri(
                getString(R.string.tambah_kehadiran_kelas_route)
            )
        }
    }

    private fun observer() {
        observe(viewModel.listKehadiranResp) {
            if (it != null) {
                binding.rvListKehadiranSiswa.layoutManager = LinearLayoutManager(requireActivity())
                binding.rvListKehadiranSiswa.adapter = KehadiranKelasAdapter(it)
            }
        }
    }
}

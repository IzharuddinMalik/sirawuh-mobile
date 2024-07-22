package com.sirawuh.ui.kelas

import com.sirawuh.R
import com.sirawuh.core.base.BaseFragment
import com.sirawuh.databinding.FragmentKelasBinding
import com.sirawuh.utils.navigateInclusivelyToUri
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KelasFragment:
    BaseFragment<FragmentKelasBinding>(FragmentKelasBinding::inflate) {

    override fun onCreated() {
        setupListener()
    }

    private fun setupListener() {
        binding.apply {

            llInformasiKelas.setOnClickListener {
                navigateInclusivelyToUri(R.string.informasi_kelas_route)
            }

            llKehadiranKelas.setOnClickListener {
                navigateInclusivelyToUri(R.string.kehadiran_kelas_route)
            }

            llHaidSiswaKelas.setOnClickListener {
                navigateInclusivelyToUri(R.string.haid_kelas_route)
            }

            llPiketKelas.setOnClickListener {
                navigateInclusivelyToUri(R.string.piket_kelas_route)
            }

            llKasKelas.setOnClickListener {
                navigateInclusivelyToUri(R.string.kas_kelas_route)
            }
        }
    }
}

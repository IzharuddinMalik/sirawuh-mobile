package com.bebasasa.ui.kelas

import com.bebasasa.R
import com.bebasasa.core.base.BaseFragment
import com.bebasasa.databinding.FragmentKelasBinding
import com.bebasasa.utils.navigateInclusivelyToUri
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

            llPenilaianKelas.setOnClickListener {
                navigateInclusivelyToUri(R.string.penilaian_kelas_route)
            }

            llTanyaJawabKelas.setOnClickListener {
                navigateInclusivelyToUri(R.string.tanya_jawab_kelas_route)
            }
        }
    }
}

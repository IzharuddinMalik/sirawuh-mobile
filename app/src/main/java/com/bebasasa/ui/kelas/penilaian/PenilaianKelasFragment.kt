package com.bebasasa.ui.kelas.penilaian

import androidx.fragment.app.viewModels
import com.bebasasa.R
import com.bebasasa.core.base.BaseFragment
import com.bebasasa.databinding.FragmentPenilaianKelasBinding
import com.bebasasa.utils.navigateInclusivelyToUri
import com.bebasasa.utils.navigateToUri
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PenilaianKelasFragment :
    BaseFragment<FragmentPenilaianKelasBinding>(FragmentPenilaianKelasBinding::inflate) {

    override fun onCreated() {
        binding.apply {
            toolbarPenilaianKelas.ivBack.setOnClickListener {
                navigateToUri(
                    getString(R.string.home_route).replace(
                        "{destination}",
                        getString(R.string.label_kelas)
                    )
                )
            }

            toolbarPenilaianKelas.tvTitle.text = getString(R.string.label_penilaian_kelas)
        }

        setupListener()
    }

    private fun setupListener() {
        binding.llPenilaianKelas9a.setOnClickListener {
            navigateInclusivelyToUri(R.string.list_kelas_9a_penilaian_route)
        }

        binding.llPenilaianKelas9b.setOnClickListener {
            navigateInclusivelyToUri(R.string.list_kelas_9b_penilaian_route)
        }
    }
}

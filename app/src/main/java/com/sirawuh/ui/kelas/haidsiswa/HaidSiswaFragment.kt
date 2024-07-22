package com.sirawuh.ui.kelas.haidsiswa

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sirawuh.R
import com.sirawuh.core.base.BaseFragment
import com.sirawuh.databinding.FragmentHaidsiswaKelasBinding
import com.sirawuh.utils.navigateInclusivelyToUri
import com.sirawuh.utils.navigateToUri
import com.sirawuh.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HaidSiswaFragment:
    BaseFragment<FragmentHaidsiswaKelasBinding>(FragmentHaidsiswaKelasBinding::inflate) {

    private val viewModel: HaidSiswaViewModel by viewModels()

    override fun onCreated() {
        viewModel.RequestHandler().getListHaidSiswa()

        initComponent()
        setupListener()
        observer()
    }

    private fun initComponent() {
        binding.apply {
            toolbarHaidSiswa.ivBack.setOnClickListener {
                navigateToUri(
                    getString(R.string.home_route).replace(
                        "{destination}",
                        getString(R.string.label_kelas)
                    )
                )
            }
            toolbarHaidSiswa.tvTitle.text = getString(R.string.label_haidsiswa_kelas)
        }
    }

    private fun setupListener() {
        binding.btnTambahInfoHaid.setOnClickListener {
            navigateToUri(R.string.tambah_haid_kelas_route)
        }
    }

    private fun observer() {
        observe(viewModel.listSiswaHaidResp) {
            binding.apply {
                if (it != null) {
                    rvListHaidSiswa.layoutManager = LinearLayoutManager(requireActivity())
                    rvListHaidSiswa.adapter = HaidSiswaAdapter(it)
                }
            }
        }
    }
}

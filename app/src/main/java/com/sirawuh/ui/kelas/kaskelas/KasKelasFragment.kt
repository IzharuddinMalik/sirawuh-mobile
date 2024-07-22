package com.sirawuh.ui.kelas.kaskelas

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sirawuh.R
import com.sirawuh.core.base.BaseFragment
import com.sirawuh.databinding.FragmentKasKelasBinding
import com.sirawuh.utils.navigateToUri
import com.sirawuh.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KasKelasFragment:
    BaseFragment<FragmentKasKelasBinding>(FragmentKasKelasBinding::inflate) {

    private val viewModel: KasKelasViewModel by viewModels()

    override fun onCreated() {
        viewModel.RequestHandler().getListKasKelas()

        initComponent()
        setupListener()
        observer()
    }

    private fun initComponent() {
        binding.apply {
            toolbarKasKelas.ivBack.setOnClickListener {
                navigateToUri(
                    getString(R.string.home_route).replace(
                        "{destination}",
                        getString(R.string.label_kelas)
                    )
                )
            }

            toolbarKasKelas.tvTitle.text = getString(R.string.label_kas_kelas)
        }
    }

    private fun setupListener() {
        binding.btnTambahBayarKas.setOnClickListener {
            navigateToUri(R.string.tambah_bayarkas_kelas_route)
        }
    }

    private fun observer() {
        observe(viewModel.listKasKelasResp) {
            if (it != null) {
                binding.rvListKasKelas.apply {
                    layoutManager = LinearLayoutManager(requireActivity())
                    adapter = KasKelasAdapter(it)
                }
            }
        }
    }
}

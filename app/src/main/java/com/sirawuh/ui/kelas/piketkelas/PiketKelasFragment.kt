package com.sirawuh.ui.kelas.piketkelas

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sirawuh.R
import com.sirawuh.core.base.BaseFragment
import com.sirawuh.databinding.FragmentPiketKelasBinding
import com.sirawuh.utils.navigateToUri
import com.sirawuh.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PiketKelasFragment:
    BaseFragment<FragmentPiketKelasBinding>(FragmentPiketKelasBinding::inflate) {

    private val viewModel: PiketKelasViewModel by viewModels()

    override fun onCreated() {
        viewModel.RequestHandler().getListPiket()

        initComponent()
        setupListener()
        observer()
    }

    private fun initComponent() {
        binding.apply {
            toolbarPiketKelas.ivBack.setOnClickListener {
                navigateToUri(
                    getString(R.string.home_route).replace(
                        "{destination}",
                        getString(R.string.label_kelas)
                    )
                )
            }

            toolbarPiketKelas.tvTitle.text = getString(R.string.label_piket_kelas)
        }
    }

    private fun setupListener() {
        binding.btnTambahPiketKelas.setOnClickListener {
            navigateToUri(R.string.tambah_piket_kelas_route)
        }
    }

    private fun observer() {
        observe(viewModel.listPiketResp) {
            if (it != null) {
                binding.rvListPiketKelas.apply {
                    layoutManager = LinearLayoutManager(requireActivity())
                    adapter = PiketKelasAdapter(it)
                }
            }
        }
    }
}

package com.sirawuh.ui.kelas.informasi

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sirawuh.core.base.BaseFragment
import com.sirawuh.core.callback.ClickCallback
import com.sirawuh.core.customview.CustomDialog
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.utils.navigateInclusivelyToUri
import com.sirawuh.utils.navigateToUri
import com.sirawuh.utils.observe
import com.google.android.material.snackbar.Snackbar
import com.sirawuh.R
import com.sirawuh.data.domain.DataPengumumanResponse
import com.sirawuh.databinding.FragmentInformasiKelasBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InformasiKelasFragment :
    BaseFragment<FragmentInformasiKelasBinding>(FragmentInformasiKelasBinding::inflate) {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val viewModel: InformasiKelasViewModel by viewModels()

    override fun onCreated() {
        viewModel.RequestHandler().getListInformasi()
        binding.apply {
            toolbarInformasiKelas.ivBack.setOnClickListener {
                navigateToUri(
                    getString(R.string.home_route).replace(
                        "{destination}",
                        getString(R.string.label_kelas)
                    )
                )
            }

            toolbarInformasiKelas.tvTitle.text = getString(R.string.label_informasi_kelas)
        }

        initComponent()
        observer()
        setupListener()
    }

    private fun initComponent() {
//        when (preferenceHelper.getNipNisSession()) {
//            "15" -> {
//                binding.btnBuatInformasiKelas.visibility = View.VISIBLE
//            } else -> {
//                binding.btnBuatInformasiKelas.visibility = View.GONE
//            }
//        }
    }

    private fun setupListener() {
        binding.btnBuatInformasiKelas.setOnClickListener {
            navigateInclusivelyToUri(R.string.tambah_informasi_kelas_route)
        }
    }

    private fun observer() {
        observe(viewModel.listInformasiKelas) {
            binding.rvListInformasiKelas.apply {
                this.layoutManager = LinearLayoutManager(requireActivity())
                this.adapter = it?.let { items ->
                    InformasiKelasAdapter(items,
                        object : ClickCallback<DataPengumumanResponse> {
                            override fun onClick(data: DataPengumumanResponse) {
                                // Detail
                                CustomDialog.showPopupDetailInformasi(
                                    requireContext(),
                                    layoutInflater,
                                    data
                                )
                            }
                        },
                        object : ClickCallback<DataPengumumanResponse> {
                            override fun onClick(data: DataPengumumanResponse) {
                                // Edit
                                navigateToUri(
                                    getString(R.string.ubah_informasi_kelas_route)
                                        .replace("{idinformasi}", data.idpengumumankelas)
                                        .replace("{judulinformasi}", data.judulpengumuman)
                                        .replace("{deskripsi}", data.isipengumuman)
                                )
                            }
                        },
                        object : ClickCallback<DataPengumumanResponse> {
                            override fun onClick(data: DataPengumumanResponse) {
                                // Delete
                                viewModel.StateHandler().setIdPapanInformasi(data.idpengumumankelas)
                                val customDialog = CustomDialog.ComponentDialog(
                                    title = getString(R.string.label_button_delete),
                                    message = getString(R.string.label_desc_delete),
                                    buttonLeft = getString(R.string.label_no),
                                    buttonRight = getString(R.string.label_yes)
                                )

                                CustomDialog.showPopUpConfirmation(
                                    requireContext(),
                                    layoutInflater,
                                    customDialog,
                                    onConfirmed = {
                                        viewModel.RequestHandler().hapusInformasi()
                                    }
                                )
                            }
                        },
                        preferenceHelper)
                }
            }
        }

        observe(viewModel.error) {
            if (it != "") Snackbar.make(binding.containerInformasiKelas, it, Snackbar.LENGTH_SHORT)
                .show()
        }

        observe(viewModel.hapusInformasiResp) {
            if (it?.success == 1) {
                viewModel.RequestHandler().getListInformasi()
            }
        }
    }
}

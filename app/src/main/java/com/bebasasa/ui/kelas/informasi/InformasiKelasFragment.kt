package com.bebasasa.ui.kelas.informasi

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bebasasa.R
import com.bebasasa.core.base.BaseFragment
import com.bebasasa.core.callback.ClickCallback
import com.bebasasa.core.customview.CustomDialog
import com.bebasasa.data.domain.ItemInformasiKelasResp
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.databinding.FragmentInformasiKelasBinding
import com.bebasasa.utils.navigateInclusivelyToUri
import com.bebasasa.utils.navigateToUri
import com.bebasasa.utils.observe
import com.google.android.material.snackbar.Snackbar
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
        when (preferenceHelper.getTipeUserSession()) {
            "1" -> {
                binding.btnBuatInformasiKelas.visibility = View.GONE
            }

            "2" -> {
                binding.btnBuatInformasiKelas.visibility = View.VISIBLE
            }
        }
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
                        object : ClickCallback<ItemInformasiKelasResp> {
                            override fun onClick(data: ItemInformasiKelasResp) {
                                // Detail
                                CustomDialog.showPopupDetailInformasi(
                                    requireContext(),
                                    layoutInflater,
                                    data
                                )
                            }
                        },
                        object : ClickCallback<ItemInformasiKelasResp> {
                            override fun onClick(data: ItemInformasiKelasResp) {
                                // Edit
                                navigateToUri(
                                    getString(R.string.ubah_informasi_kelas_route)
                                        .replace("{idinformasi}", data.idpapaninformasi)
                                        .replace("{judulinformasi}", data.judulinformasi)
                                        .replace("{deskripsi}", data.deskripsiinformasi)
                                )
                            }
                        },
                        object : ClickCallback<ItemInformasiKelasResp> {
                            override fun onClick(data: ItemInformasiKelasResp) {
                                // Delete
                                viewModel.StateHandler().setIdPapanInformasi(data.idpapaninformasi)
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

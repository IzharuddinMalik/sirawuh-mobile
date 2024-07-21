package com.bebasasa.ui.kelas.penilaian

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bebasasa.R
import com.bebasasa.core.base.BaseFragment
import com.bebasasa.core.callback.ClickCallback
import com.bebasasa.core.customview.CustomDialog
import com.bebasasa.data.domain.DataPenilaianResp
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.databinding.FragmentPenilaianKelas9bBinding
import com.bebasasa.utils.navigateInclusivelyToUri
import com.bebasasa.utils.navigateToUri
import com.bebasasa.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PenilaianKelas9BFragment :
    BaseFragment<FragmentPenilaianKelas9bBinding>(FragmentPenilaianKelas9bBinding::inflate) {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val viewModel: PenilaianViewModel by viewModels()

    override fun onCreated() {
        viewModel.RequestHandler().getListPenilaian9b()

        initComponent()
        setupListener()
        observer()
    }

    private fun initComponent() {
        when (preferenceHelper.getTipeUserSession()) {
            "1" -> {
                binding.btnBuatPenilaianKelas9b.visibility = View.GONE
            }

            "2" -> {
                binding.btnBuatPenilaianKelas9b.visibility = View.VISIBLE
            }
        }
    }

    private fun setupListener() {
        binding.apply {
            toolbarPenilaianKelas9b.ivBack.setOnClickListener {
                navigateInclusivelyToUri(R.string.penilaian_kelas_route)
            }

            toolbarPenilaianKelas9b.tvTitle.text =
                getString(R.string.label_penilaian_kelas) + " " + getString(R.string.label_kelas_9b)
        }

        binding.btnBuatPenilaianKelas9b.setOnClickListener {
            navigateToUri(
                getString(R.string.tambah_penilaian_route)
                    .replace("{kelas}", "9B")
            )
        }
    }

    private fun observer() {
        observe(viewModel.listPenilaian9b) {
            binding.rvListPenilaianKelas9b.apply {
                this.layoutManager = LinearLayoutManager(requireActivity())
                this.adapter = it?.let { item ->
                    ListPenilaianKelasAdapter(item, object :
                        ClickCallback<DataPenilaianResp> {
                        override fun onClick(data: DataPenilaianResp) {
                            // Detail
                            navigateToUri(
                                getString(R.string.detail_penilaian_route)
                                    .replace("{tanggal}", data.tanggalpenilaian)
                                    .replace("{kelas}", "9B")
                                    .replace("{judul}", data.judulpenilaian)
                            )
                        }
                    }, object :
                        ClickCallback<DataPenilaianResp> {
                        override fun onClick(data: DataPenilaianResp) {
                            // Hapus
                            viewModel.StateHandler().setKelas("9B")
                            viewModel.StateHandler().setTanggalHapus(data.tanggalpenilaian)

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
                                    viewModel.RequestHandler().reqHapusPenilaian()
                                }
                            )
                        }
                    })
                }
            }
        }

        observe(viewModel.hapusPenilaianResp) {
            if (it?.success == 1) {
                viewModel.RequestHandler().getListPenilaian9b()
            }
        }
    }
}

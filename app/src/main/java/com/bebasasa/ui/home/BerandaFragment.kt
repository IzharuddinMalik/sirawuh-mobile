package com.bebasasa.ui.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bebasasa.R
import com.bebasasa.core.base.BaseFragment
import com.bebasasa.core.callback.ClickCallback
import com.bebasasa.core.customview.CustomDialog
import com.bebasasa.data.domain.ItemInformasiKelasResp
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.databinding.FragmentBerandaBinding
import com.bebasasa.ui.kelas.informasi.InformasiKelasViewModel
import com.bebasasa.utils.navigateInclusivelyToUri
import com.bebasasa.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BerandaFragment :
    BaseFragment<FragmentBerandaBinding>(FragmentBerandaBinding::inflate) {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val viewModel: InformasiKelasViewModel by viewModels()

    override fun onCreated() {
        viewModel.RequestHandler().getListInformasi()
        initComponent()
        setupListener()
        observer()
    }

    private fun initComponent() {
        binding.headerBeranda.tvInitialNameHome.text =
            preferenceHelper.getNamaLengkapSession()?.subSequence(0, 1)
        binding.headerBeranda.tvFullnameHome.text = preferenceHelper.getNamaLengkapSession()
    }

    private fun setupListener() {
        binding.headerBeranda.tvInitialNameHome.setOnClickListener {
            navigateInclusivelyToUri(R.string.profile_route)
        }
    }

    private fun observer() {
        observe(viewModel.listInformasiKelas) {
            binding.rvListPapanInformasiBeranda.apply {
                this.layoutManager = LinearLayoutManager(requireActivity())
                this.adapter = it?.let { items ->
                    InformasiKelasHomeAdapter(items,
                        object : ClickCallback<ItemInformasiKelasResp> {
                            override fun onClick(data: ItemInformasiKelasResp) {
                                // Detail
                                CustomDialog.showPopupDetailInformasi(
                                    requireContext(),
                                    layoutInflater,
                                    data
                                )
                            }
                        })
                }
            }
        }
    }
}

package com.sirawuh.ui.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sirawuh.R
import com.sirawuh.core.base.BaseFragment
import com.sirawuh.core.callback.ClickCallback
import com.sirawuh.core.customview.CustomDialog
import com.sirawuh.data.domain.DataPengumumanResponse
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.databinding.FragmentBerandaBinding
import com.sirawuh.ui.kelas.informasi.InformasiKelasViewModel
import com.sirawuh.ui.profile.ProfileViewModel
import com.sirawuh.utils.navigateInclusivelyToUri
import com.sirawuh.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BerandaFragment :
    BaseFragment<FragmentBerandaBinding>(FragmentBerandaBinding::inflate) {

    private val viewModel: InformasiKelasViewModel by viewModels()

    private val profileVM: ProfileViewModel by viewModels()

    override fun onCreated() {
        viewModel.RequestHandler().getListInformasi()
        profileVM.initState()
        setupListener()
        observer()
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
                        object : ClickCallback<DataPengumumanResponse> {
                            override fun onClick(data: DataPengumumanResponse) {
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

        observe(profileVM.profileResp) {
            binding.headerBeranda.tvInitialNameHome.text =
                it?.namauser?.subSequence(0, 1)
            binding.headerBeranda.tvFullnameHome.text = it?.namauser
        }
    }
}

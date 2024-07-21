package com.bebasasa.ui.kelas.tanyajawab

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bebasasa.R
import com.bebasasa.core.base.BaseFragment
import com.bebasasa.core.callback.ClickCallback
import com.bebasasa.data.domain.DataListForumResp
import com.bebasasa.databinding.FragmentTanyaJawabKelasBinding
import com.bebasasa.utils.navigateInclusivelyToUri
import com.bebasasa.utils.navigateToUri
import com.bebasasa.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TanyaJawabKelasFragment :
    BaseFragment<FragmentTanyaJawabKelasBinding>(FragmentTanyaJawabKelasBinding::inflate) {

    private val viewModel: TanyaJawabViewModel by viewModels()

    override fun onCreated() {
        viewModel.RequestHandler().getListForum()

        binding.apply {
            toolbarTanyaJawabKelas.ivBack.setOnClickListener {
                navigateToUri(
                    getString(R.string.home_route).replace(
                        "{destination}",
                        getString(R.string.label_kelas)
                    )
                )
            }

            toolbarTanyaJawabKelas.tvTitle.text = getString(R.string.label_tanyajawab_kelas)
        }

        setupListener()
        observer()
    }

    private fun setupListener() {
        binding.btnBuatTanyaJawab.setOnClickListener {
            navigateInclusivelyToUri(R.string.tambah_tanya_jawab_route)
        }
    }

    private fun observer() {
        observe(viewModel.listForumResp) {
            binding.rvListTanyaJawabKelas.apply {
                this.layoutManager = LinearLayoutManager(requireActivity())
                this.adapter = it?.let { items ->
                    ListForumAdapter(items, object :
                        ClickCallback<DataListForumResp> {
                        override fun onClick(data: DataListForumResp) {
                            // Detail
                            navigateToUri(
                                getString(R.string.detail_forum_tanyajawab_route)
                                    .replace("{idtanyajawab}", data.idtanyajawab)
                                    .replace("{judul}", data.judulpertanyaan)
                                    .replace("{nama}", data.namalengkap)
                                    .replace("{tanggal}", data.created_date)
                            )
                        }
                    })
                }
            }
        }
    }
}

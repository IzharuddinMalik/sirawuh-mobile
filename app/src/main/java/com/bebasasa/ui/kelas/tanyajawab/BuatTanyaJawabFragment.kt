package com.bebasasa.ui.kelas.tanyajawab

import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.bebasasa.R
import com.bebasasa.core.base.BaseFragment
import com.bebasasa.databinding.FragmentBuatTanyaJawabBinding
import com.bebasasa.utils.navigateInclusivelyToUri
import com.bebasasa.utils.observe
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuatTanyaJawabFragment:
    BaseFragment<FragmentBuatTanyaJawabBinding>(FragmentBuatTanyaJawabBinding::inflate) {

    private val viewModel: TanyaJawabViewModel by viewModels()

    override fun onCreated() {
        viewModel.initState()

        binding.apply {
            toolbarTanyaJawab.tvTitle.text = getString(R.string.label_tanyajawab_kelas)
            toolbarTanyaJawab.ivBack.setOnClickListener {
                navigateInclusivelyToUri(R.string.tanya_jawab_kelas_route)
            }
        }

        initComponent()
        setupListener()
        observer()
    }

    private fun initComponent() {
        binding.tfJudulTanyaJawab.doOnTextChanged { text, _, _, _ ->
            viewModel.StateHandler().setJudulPertanyaan(text.toString())
        }
    }

    private fun setupListener() {
        binding.btnBuatTanyaJawab.setOnClickListener {
            viewModel.RequestHandler().buatTanyaJawab()
        }
    }

    private fun observer() {
        observe(viewModel.judulPertanyaanValid) {
            val message = if (it == false) getString(R.string.field_empty) else ""
            if (message != "") Snackbar.make(binding.containerBuatTanyaJawab, message, Snackbar.LENGTH_SHORT).show()
        }

        observe(viewModel.isButtonEnable) {
            if (it != null) {
                binding.btnBuatTanyaJawab.isEnabled = it
                binding.btnBuatTanyaJawab.background = if (it) AppCompatResources.getDrawable(
                    requireActivity(), R.drawable.bg_bebasasa_button
                ) else AppCompatResources.getDrawable(
                    requireActivity(), R.drawable.button_disable
                )
            }
        }

        observe(viewModel.buatTanyaJawabResp) {
            if (it != null) {
                if (it.success == 1) {
                    Snackbar.make(binding.containerBuatTanyaJawab, it.message, Snackbar.LENGTH_SHORT).show()
                    navigateInclusivelyToUri(R.string.tanya_jawab_kelas_route)
                }
            }
        }

        observe(viewModel.error) {
            if (it != "") {
                Snackbar.make(binding.containerBuatTanyaJawab, it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}

package com.bebasasa.ui.kelas.penilaian

import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bebasasa.R
import com.bebasasa.core.base.BaseFragment
import com.bebasasa.core.callback.ClickCallback
import com.bebasasa.databinding.FragmentTambahPenilaianBinding
import com.bebasasa.utils.navigateInclusivelyToUri
import com.bebasasa.utils.observe
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TambahPenilaianFragment :
    BaseFragment<FragmentTambahPenilaianBinding>(FragmentTambahPenilaianBinding::inflate) {

    private val viewModel: PenilaianViewModel by viewModels()

    override fun onCreated() {
        viewModel.initState()
        kelas = arguments?.getString("kelas").orEmpty()

        when (kelas) {
            "9A" -> {
                viewModel.RequestHandler().getSiswa9a()
                viewModel.StateHandler().setKelas("9A")
                binding.rvListSiswaPenilaian9b.visibility = View.GONE
            }

            "9B" -> {
                viewModel.RequestHandler().getSiswa9b()
                viewModel.StateHandler().setKelas("9B")
                binding.rvListSiswaPenilaian9a.visibility = View.GONE
            }
        }

        initComponent()
        setupListener()
        observer()
    }

    private fun initComponent() {
        binding.tfJudulPenilaian.doOnTextChanged { text, _, _, _ ->
            viewModel.StateHandler().setJudulPenilaian(text.toString())
        }
    }

    private fun setupListener() {

        binding.apply {
            toolbarTambahNilai.tvTitle.text = getString(R.string.title_tambah_penilaian)
            toolbarTambahNilai.ivBack.setOnClickListener {
                when (kelas) {
                    "9A" -> {
                        navigateInclusivelyToUri(R.string.list_kelas_9a_penilaian_route)
                    }

                    "9B" -> {
                        navigateInclusivelyToUri(R.string.list_kelas_9b_penilaian_route)
                    }
                }
            }
        }

        binding.llSelectDatePenilaian.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Pilih Tanggal")
                    .build()

            datePicker.show(requireActivity().supportFragmentManager, "tag")

            datePicker.addOnPositiveButtonClickListener {
                viewModel.StateHandler().convertDate(it)
            }
            datePicker.addOnCancelListener {
                datePicker.dismiss()
            }
            datePicker.addOnDismissListener {
                datePicker.dismiss()
            }
        }

        binding.btnTambahPenilaian.setOnClickListener {
            viewModel.RequestHandler().tambahPenilaian()
        }
    }

    private fun observer() {
        observe(viewModel.tanggalPenilaianConvert) {
            binding.tvDatePenilaian.text = it
        }

        observe(viewModel.listSiswa9a) {
            binding.rvListSiswaPenilaian9a.apply {
                this.layoutManager = LinearLayoutManager(requireActivity())
                this.adapter = it?.let { items ->
                    FormPenilaianAdapter(items,
                        object : ClickCallback<ItemPenilaian> {
                            override fun onClick(data: ItemPenilaian) {
                                viewModel.StateHandler().setNilaiList(data.nilai)
                                viewModel.StateHandler().setNipNisList(data.nipnis)
                            }
                        }
                    )
                }
            }
        }

        observe(viewModel.listSiswa9b) {
            binding.rvListSiswaPenilaian9b.apply {
                this.layoutManager = LinearLayoutManager(requireActivity())
                this.adapter = it?.let { items ->
                    FormPenilaianAdapter(items,
                        object : ClickCallback<ItemPenilaian> {
                            override fun onClick(data: ItemPenilaian) {
                                viewModel.StateHandler().setNilaiList(data.nilai)
                                viewModel.StateHandler().setNipNisList(data.nipnis)
                            }
                        }
                    )
                }
            }
        }

        observe(viewModel.judulPenilaianValid) {
            val message = if (it == false) getString(R.string.field_empty) else ""
            if (message != "") Snackbar.make(
                binding.containerTambahPenilaian,
                message,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        observe(viewModel.isButtonEnable) {
            if (it != null) {
                binding.btnTambahPenilaian.isEnabled = it
                binding.btnTambahPenilaian.background = AppCompatResources.getDrawable(
                    requireActivity(), R.drawable.bg_bebasasa_button
                ) ?: AppCompatResources.getDrawable(
                    requireActivity(), R.drawable.button_disable
                )
            }
        }

        observe(viewModel.tambahPenilaianResp) {
            if (it != null) {
                Snackbar.make(binding.containerTambahPenilaian, it.message, Snackbar.LENGTH_SHORT)
                    .show()
                navigateInclusivelyToUri(R.string.penilaian_kelas_route)
            }
        }
    }

    companion object {
        private var kelas = ""
    }
}

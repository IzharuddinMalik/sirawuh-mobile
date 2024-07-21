package com.bebasasa.ui.kelas.penilaian

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bebasasa.R
import com.bebasasa.core.base.BaseFragment
import com.bebasasa.databinding.FragmentDetailPenilaianKelasBinding
import com.bebasasa.utils.navigateInclusivelyToUri
import com.bebasasa.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class DetailPenilaianKelasFragment :
    BaseFragment<FragmentDetailPenilaianKelasBinding>(FragmentDetailPenilaianKelasBinding::inflate) {

    private val viewModel: PenilaianViewModel by viewModels()

    override fun onCreated() {
        initComponent()
        setupListener()
        observer()
    }

    private fun initComponent() {
        kelas = arguments?.getString("kelas").orEmpty()
        tanggal = arguments?.getString("tanggal").orEmpty()
        judul = arguments?.getString("judul").orEmpty()

        when (kelas) {
            "9A" -> {
                binding.tvDetailDatePenilaian.text = convertDateWithDay(tanggal)
                binding.tvDetailKelasPenilaian.text = getString(R.string.label_kelas_9a)
                binding.tvDetailJudulPenilaian.text = judul

                viewModel.StateHandler().setTanggalHapus(tanggal)
                viewModel.RequestHandler().getDetailPenilaian9a()
                binding.rvListDetailPenilaian9b.visibility = View.GONE
            }

            "9B" -> {
                binding.tvDetailDatePenilaian.text = convertDateWithDay(tanggal)
                binding.tvDetailKelasPenilaian.text = getString(R.string.label_kelas_9b)
                binding.tvDetailJudulPenilaian.text = judul

                viewModel.StateHandler().setTanggalHapus(tanggal)
                viewModel.RequestHandler().getDetailPenilaian9b()
                binding.rvListDetailPenilaian9a.visibility = View.GONE
            }
        }
    }

    private fun setupListener() {
        binding.apply {
            when (kelas) {
                "9A" -> {
                    toolbarDetailPenilaianKelas.tvTitle.text =
                        getString(R.string.label_penilaian_kelas) + " " + getString(R.string.label_kelas_9a)
                    toolbarDetailPenilaianKelas.ivBack.setOnClickListener {
                        navigateInclusivelyToUri(R.string.list_kelas_9a_penilaian_route)
                    }
                }

                "9B" -> {
                    toolbarDetailPenilaianKelas.tvTitle.text =
                        getString(R.string.label_penilaian_kelas) + " " + getString(R.string.label_kelas_9b)
                    toolbarDetailPenilaianKelas.ivBack.setOnClickListener {
                        navigateInclusivelyToUri(R.string.list_kelas_9b_penilaian_route)
                    }
                }
            }
        }
    }

    private fun observer() {
        observe(viewModel.detailPenilaian9a) {
            binding.rvListDetailPenilaian9a.apply {
                this.layoutManager = LinearLayoutManager(requireActivity())
                this.adapter = it?.data?.let { item -> DetailPenilaianAdapter(item) }
            }
        }

        observe(viewModel.detailPenilaian9b) {
            binding.rvListDetailPenilaian9b.apply {
                this.layoutManager = LinearLayoutManager(requireActivity())
                this.adapter = it?.data?.let { item -> DetailPenilaianAdapter(item) }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateWithDay(time: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd")
        val format = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        return format.format(date.parse(time)!!)
    }

    companion object {
        var kelas = ""
        var tanggal = ""
        var judul = ""
    }
}

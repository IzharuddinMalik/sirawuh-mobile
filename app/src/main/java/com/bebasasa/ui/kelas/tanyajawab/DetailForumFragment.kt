package com.bebasasa.ui.kelas.tanyajawab

import android.annotation.SuppressLint
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bebasasa.R
import com.bebasasa.core.base.BaseFragment
import com.bebasasa.databinding.FragmentDetailForumBinding
import com.bebasasa.utils.navigateInclusivelyToUri
import com.bebasasa.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class DetailForumFragment :
    BaseFragment<FragmentDetailForumBinding>(FragmentDetailForumBinding::inflate) {

    private val viewModel: TanyaJawabViewModel by viewModels()
    override fun onCreated() {

        idTanyaJawab = arguments?.getString("idtanyajawab").orEmpty()
        judulPertanyaan = arguments?.getString("judul").orEmpty()
        author = arguments?.getString("nama").orEmpty()
        date = arguments?.getString("tanggal").orEmpty()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (isActive) kotlin.runCatching {
                    delay(duration)
                    viewModel.StateHandler().setIdTanyaJawab(idTanyaJawab)
                    viewModel.RequestHandler().detailForum()
                }
            }
        }

        viewModel.initState()

        binding.apply {
            toolbarDetailForum.ivBack.setOnClickListener {
                navigateInclusivelyToUri(R.string.tanya_jawab_kelas_route)
            }

            toolbarDetailForum.tvTitle.text = getString(R.string.title_detaiL_tanya_jawab)
        }

        initComponent()
        setupListener()
        observer()
    }

    private fun initComponent() {

        binding.tvDetailJudulPertanyaanForum.text = judulPertanyaan
        binding.tvDetailDateForum.text = convertDateWithDay(date)
        binding.tvDetailAuthorForum.text = getString(R.string.label_dibuatoleh_tanya_jawab, author)

        binding.edtMessageChatForum.doOnTextChanged { text, _, _, _ ->
            viewModel.StateHandler().setMessageForum(text.toString())
        }
    }

    private fun setupListener() {
        binding.btnKirimPesanChatForum.setOnClickListener {
            viewModel.RequestHandler().kirimPesanForum()
        }
    }

    private fun observer() {
        observe(viewModel.detaiForumResp) {
            binding.rvListChatForum.apply {
                this.layoutManager = LinearLayoutManager(requireActivity())
                this.adapter = it?.data?.let { items -> ChatForumAdapter(items) }
            }
        }

        observe(viewModel.chatForumResp) {
            if (it != null) {
                if (it.success == 1) {
                    binding.edtMessageChatForum.setText("")
                    viewModel.RequestHandler().detailForum()
                }
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
        const val duration: Long = 500L
        var idTanyaJawab = ""
        var judulPertanyaan = ""
        var author = ""
        var date = ""
    }
}

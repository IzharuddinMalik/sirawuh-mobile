package com.sirawuh.ui.profile

import android.app.ProgressDialog
import androidx.fragment.app.viewModels
import com.sirawuh.R
import com.sirawuh.core.base.BaseFragment
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.databinding.FragmentProfileBinding
import com.sirawuh.utils.KeyPrefConst
import com.sirawuh.utils.navigateInclusivelyToUri
import com.sirawuh.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment:
    BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    @Inject
    lateinit var preferenceHelper : PreferenceHelper

    private val viewModel: ProfileViewModel by viewModels()
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreated() {
        mProgressDialog = ProgressDialog(requireActivity())
        binding.apply {
            toolbarprofile.ivBack.setOnClickListener {
                navigateInclusivelyToUri(R.string.home_route)
            }

            toolbarprofile.tvTitle.text = getString(R.string.title_profile)
        }

        viewModel.initState()

        setupListener()
        observer()
    }

    private fun setupListener() {
        binding.btnLogout.setOnClickListener {
            preferenceHelper.putBoolean(KeyPrefConst.IS_LOGGED_IN, true)
            navigateInclusivelyToUri(R.string.login_route)
        }
    }

    private fun observer() {
        observe(viewModel.transparentLoading) {
            if (it) {
                mProgressDialog?.setTitle("Sedang Login")
                mProgressDialog?.setMessage("Harap menunggu...")
                mProgressDialog?.show()
            } else {
                mProgressDialog?.dismiss()
            }
        }

        observe(viewModel.profileResp) {
            binding.tvFullnameProfile.text = it?.namauser
            binding.tvInitialNameProfile.text = it?.namauser?.substring(0, 1)
            binding.tvNisnip.text = it?.nipnis
        }
    }
}

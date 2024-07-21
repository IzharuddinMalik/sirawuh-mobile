package com.bebasasa.ui.profile

import com.bebasasa.R
import com.bebasasa.core.base.BaseFragment
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.databinding.FragmentProfileBinding
import com.bebasasa.utils.navigateInclusivelyToUri
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment:
    BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun onCreated() {
        binding.apply {
            toolbarprofile.ivBack.setOnClickListener {
                navigateInclusivelyToUri(R.string.home_route)
            }

            toolbarprofile.tvTitle.text = getString(R.string.title_profile)
        }

        setupListener()
        setupView()
    }

    private fun setupView() {
        binding.tvFullnameProfile.text = preferenceHelper.getNamaLengkapSession()
        binding.tvInitialNameProfile.text = preferenceHelper.getNamaLengkapSession()?.substring(0, 1)
        binding.tvNisnip.text = preferenceHelper.getNipNisSession()
    }

    private fun setupListener() {
        binding.btnLogout.setOnClickListener {
            navigateInclusivelyToUri(R.string.login_route)
        }
    }
}

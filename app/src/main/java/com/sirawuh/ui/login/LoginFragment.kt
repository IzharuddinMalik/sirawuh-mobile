package com.sirawuh.ui.login

import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.sirawuh.core.base.BaseFragment
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.utils.KeyPrefConst
import com.sirawuh.utils.navigateInclusivelyToUri
import com.sirawuh.utils.observe
import com.google.android.material.snackbar.Snackbar
import com.sirawuh.R
import com.sirawuh.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment :
    BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreated() {
        viewModel.initState()
        initComponent()
        setupListener()
        observer()
    }

    private fun initComponent() {
        binding.tfNisLogin.doOnTextChanged { text, _, _, _ ->
            viewModel.StateHandler().setNipNis(text.toString())
        }
    }

    private fun setupListener() {
        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.RequestHandler().login()
            }
        }
    }

    private fun observer() {
        observe(viewModel.nipnisValid) {
            val message = if (!it) getString(R.string.nipnis_invalid) else ""
            if (message != "") Snackbar.make(binding.containerLogin, message, Snackbar.LENGTH_SHORT)
                .show()
        }

        observe(viewModel.isButtonEnable) {
            binding.btnLogin.isEnabled = it
            if (it) {
                binding.btnLogin.background = AppCompatResources.getDrawable(
                    requireActivity(), R.drawable.bg_bebasasa_button
                ) ?: AppCompatResources.getDrawable(requireActivity(), R.drawable.button_disable)
            }
        }

        observe(viewModel.loginResp) {
            if (it != null) {
                preferenceHelper.saveNipNisSession(it.data.nipnis)
                preferenceHelper.saveStatusUserSession(it.data.statususer)
                preferenceHelper.putBoolean(KeyPrefConst.IS_LOGGED_IN, true)
                navigateInclusivelyToUri(R.string.home_route)
            }
        }

        observe(viewModel.error) {
            if (it != "") Snackbar.make(binding.containerLogin, it, Snackbar.LENGTH_SHORT).show()
        }
    }
}

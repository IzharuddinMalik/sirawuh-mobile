package com.bebasasa.ui.login

import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.bebasasa.R
import com.bebasasa.core.base.BaseFragment
import com.bebasasa.data.source.local.PreferenceHelper
import com.bebasasa.databinding.FragmentLoginBinding
import com.bebasasa.utils.KeyPrefConst
import com.bebasasa.utils.navigateInclusivelyToUri
import com.bebasasa.utils.observe
import com.google.android.material.snackbar.Snackbar
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
                preferenceHelper.saveUserSession(it.data)
                preferenceHelper.saveIdSession(it.data.iduser)
                preferenceHelper.putBoolean(KeyPrefConst.IS_LOGGED_IN, true)
                navigateInclusivelyToUri(R.string.home_route)
            }
        }

        observe(viewModel.error) {
            if (it != "") Snackbar.make(binding.containerLogin, it, Snackbar.LENGTH_SHORT).show()
        }
    }
}

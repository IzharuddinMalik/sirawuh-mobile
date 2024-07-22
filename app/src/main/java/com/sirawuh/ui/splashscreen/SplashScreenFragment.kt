package com.sirawuh.ui.splashscreen

import android.os.Handler
import com.sirawuh.R
import com.sirawuh.core.base.BaseFragment
import com.sirawuh.data.source.local.PreferenceHelper
import com.sirawuh.databinding.FragmentSplashscreenBinding
import com.sirawuh.utils.KeyPrefConst
import com.sirawuh.utils.navigateToUri
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenFragment :
    BaseFragment<FragmentSplashscreenBinding>(FragmentSplashscreenBinding::inflate) {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun onCreated() {
        Handler().postDelayed({
            val isLogin = preferenceHelper.getBoolean(KeyPrefConst.IS_LOGGED_IN)
            if (isLogin) {
                navigateToUri(R.string.home_route)
            } else {
                navigateToUri(R.string.login_route)
            }
        }, delayMilis)
    }

    companion object {
        const val delayMilis: Long = 3000
    }
}

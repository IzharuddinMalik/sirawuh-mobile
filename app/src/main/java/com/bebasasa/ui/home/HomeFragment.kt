package com.bebasasa.ui.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bebasasa.R
import com.bebasasa.core.base.BaseFragment
import com.bebasasa.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment:
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onCreated() {
        val navView: BottomNavigationView = binding.navView

        val navController = requireActivity().findNavController(R.id.nav_host_fragment_home)
        navView.setupWithNavController(navController)

        destination = arguments?.getString("destination").orEmpty()

        when (destination) {
            getString(R.string.label_home) -> {
                selectedMenu(R.id.navigation_home)
            }

            getString(R.string.label_kelas) -> {
                selectedMenu(R.id.navigation_kelas)
            }
        }

        navView.setOnItemSelectedListener {
            selectedMenu(it.itemId)
            destination = it.title.toString()
            arguments?.remove("destination")
            true
        }
    }

    private fun selectedMenu(id: Int, bundle: Bundle? = null) {
        navController()?.navigate(id, bundle)
    }

    private fun navController(): NavController? {
        return kotlin.runCatching {
            val tag = getString(R.string.route_home_host)
            val hostFragment = childFragmentManager.findFragmentByTag(tag) as NavHostFragment

            hostFragment.navController
        }.getOrNull()
    }

    companion object {
        private var destination = ""
    }
}

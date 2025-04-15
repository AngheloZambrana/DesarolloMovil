package com.example.loginconnav_saveargs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loginconnav_saveargs.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private val viewModel: SplashViewModel by viewModels()
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val splashIcon = binding.splashIcon
        splashIcon.alpha = 0f
        splashIcon.animate()
            .alpha(1f)
            .setDuration(1000)
            .start()

        viewModel.checkAuthStatus().observe(viewLifecycleOwner) { isLoggedIn ->
            if (isLoggedIn) {
                val username = viewModel.getUsername()
                val action = SplashFragmentDirections.actionSplashToDashboard(username)
                findNavController().navigate(action)
            } else {
                val action = SplashFragmentDirections.actionSplashToLogin()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
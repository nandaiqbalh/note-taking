package com.nandaiqbalh.notetaking.presentation.ui.login

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.nandaiqbalh.notetaking.R
import com.nandaiqbalh.notetaking.data.local.user.UserEntity
import com.nandaiqbalh.notetaking.databinding.FragmentLoginBinding
import com.nandaiqbalh.notetaking.di.ServiceLocator
import com.nandaiqbalh.notetaking.util.viewModelFactory
import com.nandaiqbalh.notetaking.wrapper.Resource

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModelFactory {
        LoginViewModel(ServiceLocator.provideServiceLocator(requireContext()))
    }
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(LOGIN_SHARED_PREF, MODE_PRIVATE)

        binding.btnLogin.setOnClickListener { checkLogin() }

        if (isLoginInfoValid()) {
            navigateToHome()
        }
        binding.tvRegisterHere.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun checkLogin() {
        if (validateInput()){
            val username = binding.etUsername.text.toString()
            viewModel.getUser(username)

            viewModel.getUser.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> checkUser(it.payload)
                    is Resource.Error -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                    else -> {}
                }
            }

        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        if (username.isEmpty()) {
            isValid = false
            binding.etUsername.error = "Please input this field!"
        }
        if (password.isEmpty()) {
            isValid = false
            Toast.makeText(requireContext(), "Please input this field!", Toast.LENGTH_SHORT).show()        }
        return isValid
    }

    private fun checkUser(user: UserEntity?) {
        user?.let {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            val userLoggedIn = username == user.username && password == user.password
            if (userLoggedIn) {
                navigateToHome()
                Toast.makeText(context, "Successfully log in!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Wrong credential!", Toast.LENGTH_SHORT).show()
            }
            saveLoginInfo(userLoggedIn)

        }
    }

    private fun saveLoginInfo(loginInfo: Boolean){
        sharedPreferences.edit {
            putBoolean(LOGGED_IN_KEY, loginInfo)
        }
    }
    private fun isLoginInfoValid(): Boolean {
        return sharedPreferences.getBoolean(LOGGED_IN_KEY, false)
    }
    private fun navigateToHome() {
        val option = NavOptions.Builder()
            .setPopUpTo(R.id.loginFragment, true)
            .build()
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment, null, option)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val LOGIN_SHARED_PREF = "login_shared_pref"
        const val LOGGED_IN_KEY = "logged_in"
    }
}
package com.nandaiqbalh.notetaking.presentation.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nandaiqbalh.notetaking.R
import com.nandaiqbalh.notetaking.data.local.user.UserEntity
import com.nandaiqbalh.notetaking.databinding.FragmentRegisterBinding
import com.nandaiqbalh.notetaking.di.ServiceLocator
import com.nandaiqbalh.notetaking.util.viewModelFactory

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModelFactory {
        RegisterViewModel(ServiceLocator.provideServiceLocator(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            registerUser()
        }

    }

    private fun registerUser() {
        if (validateInput()) {
            val user = UserEntity(
                username = binding.etUsername.text.toString(),
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
            viewModel.registerUser(user)
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            Toast.makeText(context, "Successfully to register!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        if (username.isEmpty()) {
            isValid = false
            binding.etUsername.error = "Please input this field!"
        }
        if (email.isEmpty()) {
            isValid = false
            binding.etEmail.error = "Please input this field!"
        }
        if (password.isEmpty()) {
            isValid = false
            Toast.makeText(requireContext(), "Please input this field!", Toast.LENGTH_SHORT).show()
        }
        if (confirmPassword.isEmpty()) {
            isValid = false
            Toast.makeText(requireContext(), "Please input this field!", Toast.LENGTH_SHORT).show()
        }
        if (password != confirmPassword) {
            isValid = false
            Toast.makeText(requireContext(), "Password does not match!", Toast.LENGTH_SHORT).show()
        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
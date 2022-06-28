package com.example.githubapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentAuthBinding
import com.example.githubapp.domain.utils.bindTextTwoWay
import com.example.githubapp.domain.view_models.AuthViewModel
import com.example.githubapp.domain.view_models.AuthViewModel.Action
import com.example.githubapp.domain.view_models.AuthViewModel.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentAuthBinding.inflate(layoutInflater)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        with(binding) {
            editTextToken.bindTextTwoWay(viewModel.token, viewLifecycleOwner)
            buttonAuth.setOnClickListener {
                viewModel.onPressButton()
            }

            viewModel.state.observe(viewLifecycleOwner) { state ->
                progressAuth.visibility = if (state == State.Loading) View.VISIBLE else View.GONE
                buttonAuth.text = if (state == State.Loading) "" else getText(R.string.text_signIn_button)
                buttonAuth.isClickable = state != State.Loading
                textLayout.error = if (state is State.InvalidInput) state.reason else null
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.actions.collect {
                handleAction(it)
            }
        }
    }

    private fun handleAction(action: Action) {
        when (action) {
            Action.RouteToDetail -> navigateToDetail()
            is Action.ShowError -> showDialog(action.message)
        }
    }

    private fun navigateToDetail() {
        findNavController().navigate(R.id.action_authFragment_to_repoFragment)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogStyle)
        val dialog = builder
            .setTitle(getText(R.string.text_dialog_title))
            .setMessage("$message\nInformation for developers")
            .setPositiveButton(getText(R.string.text_possible_button), null)
            .create()

        dialog.show()
    }
}
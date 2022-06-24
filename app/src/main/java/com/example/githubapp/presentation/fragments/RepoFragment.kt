package com.example.githubapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.githubapp.R
import com.example.githubapp.databinding.FragmentRepoBinding
import com.example.githubapp.domain.view_models.RepoViewModel
import com.example.githubapp.domain.view_models.RepoViewModel.State
import com.example.githubapp.presentation.adapters.RepoRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoFragment : Fragment() {
    private lateinit var binding: FragmentRepoBinding
    private val viewModel: RepoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentRepoBinding.inflate(layoutInflater)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val decorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        decorator.setDrawable(resources.getDrawable((R.drawable.recycler_decorator), requireContext().theme))
        binding.recyclerViewRepo.addItemDecoration(decorator)

        val adapter = RepoRecyclerViewAdapter(onRepoItemClick = { owner, repoName ->
            findNavController().navigate(R.id.action_repoFragment_to_detailFragment)
        })
        binding.recyclerViewRepo.adapter = adapter

        binding.repoSomethingError.buttonRefresh.setOnClickListener{ viewModel.loadRepoList() }
        binding.repoConnectionError.buttonRefresh.setOnClickListener{ viewModel.loadRepoList() }
        binding.repoEmptyError.buttonRefresh.setOnClickListener{ viewModel.loadRepoList() }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state is State.Loaded)
                adapter.data = state.repos
            binding.repoLoadingState.root.visibility = if (state == State.Loading) View.VISIBLE else View.GONE
            binding.repoEmptyError.root.visibility = if (state == State.Empty) View.VISIBLE else View.GONE
            binding.repoConnectionError.root.visibility = if (state == State.ConnectionError) View.VISIBLE else View.GONE

            if (state is State.Error) {
                binding.repoSomethingError.textViewSomeErrorText.text = state.error
                binding.repoSomethingError.root.visibility = View.VISIBLE
            } else binding.repoSomethingError.root.visibility = View.GONE
        }

        return binding.root
    }
}
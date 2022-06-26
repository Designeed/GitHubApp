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
import com.example.githubapp.presentation.fragments.DetailFragment.Companion.generateDetailArguments
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
            findNavController().navigate(R.id.action_repoFragment_to_detailFragment, generateDetailArguments(owner, repoName))
        })

        with(binding) {
            recyclerViewRepo.adapter = adapter

            repoSomethingError.buttonRefresh.setOnClickListener{ viewModel.loadRepoList() }
            repoConnectionError.buttonRefresh.setOnClickListener{ viewModel.loadRepoList() }
            repoEmptyError.buttonRefresh.setOnClickListener{ viewModel.loadRepoList() }

            viewModel.state.observe(viewLifecycleOwner) { state ->
                if (state is State.Loaded)
                    adapter.data = state.repos
                repoLoadingState.root.visibility = if (state == State.Loading) View.VISIBLE else View.GONE
                repoEmptyError.root.visibility = if (state == State.Empty) View.VISIBLE else View.GONE
                repoConnectionError.root.visibility = if (state == State.ConnectionError) View.VISIBLE else View.GONE

                if (state is State.Error) {
                    repoSomethingError.textViewSomeErrorText.text = state.error
                    repoSomethingError.root.visibility = View.VISIBLE
                } else repoSomethingError.root.visibility = View.GONE
            }
        }

        return binding.root
    }
}
package com.example.githubapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.githubapp.databinding.FragmentRepoBinding
import com.example.githubapp.domain.view_models.DetailViewModel

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentRepoBinding
    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepoBinding.inflate(layoutInflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = args.repoName
        return binding.root
    }
}
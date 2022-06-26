package com.example.githubapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import br.tiagohm.markdownview.css.styles.Github
import com.example.githubapp.databinding.FragmentDetailBinding
import com.example.githubapp.domain.utils.DetailArguments
import com.example.githubapp.domain.view_models.DetailViewModel
import com.example.githubapp.domain.view_models.DetailViewModel.State
import com.example.githubapp.domain.view_models.DetailViewModel.ReadmeState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = requireNotNull(requireArguments().getString(DetailArguments.REPO_NAME_KEY))

        with(binding) {

            Github().let {
                it.addRule("h1", "color: white")
                it.addRule("body", "background-color: #0D1117", "color: gray")
                markdownView.addStyleSheet(it)
            }

            connectionErrorDetail.buttonRefresh.setOnClickListener { viewModel.loadInfo() }
            buttonLink.setOnClickListener { viewModel.onLinkPress(buttonLink.text.toString()) }

            viewModel.state.observe(viewLifecycleOwner) { state ->
                loadingDetail.root.visibility = if (state == State.Loading) View.VISIBLE else View.GONE
                connectionErrorDetail.root.visibility = if (state == State.Error) View.VISIBLE else View.GONE

                if (state is State.Loaded) {
                    buttonLink.text = state.repoDetails.url
                    textViewNameOfLicense.text = state.repoDetails.license
                    textViewCountStars.text = state.repoDetails.stars
                    textViewCountForks.text = state.repoDetails.forks
                    textViewCountWatchers.text = state.repoDetails.watchers
                    scrollViewDetail.visibility = View.VISIBLE

                    progressReadme.visibility = if (state.readmeState == ReadmeState.Loading) View.VISIBLE else View.GONE
                    when(state.readmeState) {
                        is ReadmeState.Loaded -> markdownView.loadMarkdown(state.readmeState.markdown)
                        is ReadmeState.Error -> markdownView.loadMarkdown(state.readmeState.errorMessage)
                        is ReadmeState.Empty -> markdownView.loadMarkdown(state.readmeState.emptyMessage)
                        is ReadmeState.ConnectionErrorState -> connectionErrorDetail.root.visibility = View.VISIBLE
                        else -> connectionErrorDetail.root.visibility = View.GONE

                    }
                } else scrollViewDetail.visibility = View.GONE
            }
        }

        return binding.root
    }

    companion object {
        fun generateDetailArguments(ownerName: String, repoName: String) : Bundle {
            return bundleOf(
                DetailArguments.OWNER_NAME_KEY to ownerName,
                DetailArguments.REPO_NAME_KEY to repoName
            )
        }
    }
}
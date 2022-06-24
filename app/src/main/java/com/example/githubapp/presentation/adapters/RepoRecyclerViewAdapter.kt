package com.example.githubapp.presentation.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.databinding.RecyclerViewItemBinding
import com.example.githubapp.domain.models.Repo

class RepoRecyclerViewAdapter(
    private val onRepoItemClick: (owner: String, repoName: String) -> Unit
) : RecyclerView.Adapter<RepoRecyclerViewAdapter.ViewHolder>() {
    private lateinit var binding: RecyclerViewItemBinding
    private lateinit var context: Context

    var data = listOf<Repo>()
    set(newValue) {
        field = newValue
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        binding = RecyclerViewItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRepo = data[position]
        with(holder.binding) {
            root.setOnClickListener {
                onRepoItemClick(currentRepo.owner, currentRepo.name)
            }
            textViewRepoName.text = currentRepo.name
            textViewLanguage.text = currentRepo.language
            textViewLanguage.setTextColor(currentRepo.languageColor ?: Color.WHITE)
            textViewDescription.text = currentRepo.description
        }
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(
        val binding: RecyclerViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
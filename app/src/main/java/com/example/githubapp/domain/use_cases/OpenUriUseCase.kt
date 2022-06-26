package com.example.githubapp.domain.use_cases

import android.content.Context
import android.content.Intent
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OpenUriUseCase
@Inject
constructor(
    @ApplicationContext
    private val context: Context
) {
    fun execute(link: String) {
        val uri = Uri.parse("http://$link")
        Intent(Intent.ACTION_VIEW, uri).let {
            context.startActivity(it)
        }
    }
}
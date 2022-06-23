package com.example.githubapp.domain.use_cases

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetStringFromResourcesUseCase
@Inject
constructor(
    @ApplicationContext
    private val context: Context
) {
    fun execute(@StringRes id: Int) : String =
        context.getString(id)
}
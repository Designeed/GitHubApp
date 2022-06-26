package com.example.githubapp.domain.view_models

import androidx.lifecycle.*
import com.example.githubapp.R
import com.example.githubapp.domain.models.RepoDetails
import com.example.githubapp.domain.use_cases.GetStringFromResourcesUseCase
import com.example.githubapp.domain.use_cases.GetTokenUseCase
import com.example.githubapp.domain.use_cases.OpenUriUseCase
import com.example.githubapp.domain.use_cases.request_use_cases.GetReadmeContentUseCase
import com.example.githubapp.domain.use_cases.request_use_cases.GetRepoDetailUseCase
import com.example.githubapp.domain.utils.ConnectionErrorException
import com.example.githubapp.domain.utils.DetailArguments
import com.example.githubapp.domain.utils.NotFoundedException
import com.example.githubapp.domain.utils.ServerNotRespondingException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTokenUseCase: GetTokenUseCase,
    private val openUriUseCase: OpenUriUseCase,
    private val getRepoDetailUseCase: GetRepoDetailUseCase,
    private val getReadmeContentUseCase: GetReadmeContentUseCase,
    private val getStringFromResourcesUseCase: GetStringFromResourcesUseCase
): ViewModel() {
    private val _emptyReadmeMessage = getStringFromResourcesUseCase.execute(R.string.no_readme)
    private val _cannotOpenMessage = getStringFromResourcesUseCase.execute(R.string.text_exception_cannot_open)

    private val _state = MutableLiveData<State>()

    val state: LiveData<State>
        get() = _state

    init {
        loadInfo()
    }

    fun loadInfo() {
        viewModelScope.launch {
            _state.postValue(State.Loading)
            val token = getTokenUseCase.execute() ?: ""
            val owner = savedStateHandle.get<String>(DetailArguments.OWNER_NAME_KEY) ?: ""
            val repoName = savedStateHandle.get<String>(DetailArguments.REPO_NAME_KEY) ?: ""

            try {
                val repoInfo = getRepoDetailUseCase.execute(token, owner, repoName)
                val currentState = State.Loaded(repoInfo, ReadmeState.Loading)
                _state.postValue(currentState)

                loadReadme(token, owner, repoName, currentState)
            }  catch (ex: Exception) {
                _state.postValue(State.Error)
            }
        }
    }

    fun onLinkPress(link: String) = openUriUseCase.execute(link)

    private suspend fun loadReadme(token: String, owner: String, repoName: String, state: State.Loaded) {
        try {
            val readmeContent = getReadmeContentUseCase.execute(token, owner, repoName)

            _state.postValue(state.copy(
                state.repoDetails,
                ReadmeState.Loaded(readmeContent)
            ))
        } catch (ex: ServerNotRespondingException) {
            _state.postValue(state.copy(
                state.repoDetails,
                ReadmeState.ConnectionErrorState
            ))
        } catch (ex: ConnectionErrorException) {
            _state.postValue(state.copy(
                state.repoDetails,
                ReadmeState.ConnectionErrorState
            ))
        } catch (ex: NotFoundedException) {
            _state.postValue(state.copy(
                state.repoDetails,
                ReadmeState.Error(_emptyReadmeMessage)
            ))
        } catch (ex: Exception) {
            _state.postValue(state.copy(
                state.repoDetails,
                ReadmeState.Error(_cannotOpenMessage)
            ))
        }
    }

    sealed interface State {
        object Loading : State
        object Error : State
        data class Loaded(
            val repoDetails: RepoDetails,
            val readmeState: ReadmeState
        ) : State
    }

    sealed interface ReadmeState {
        object Loading : ReadmeState
        object ConnectionErrorState : ReadmeState
        data class Empty(val emptyMessage: String) : ReadmeState
        data class Error(val errorMessage: String) : ReadmeState
        data class Loaded(val markdown: String) : ReadmeState
    }
}
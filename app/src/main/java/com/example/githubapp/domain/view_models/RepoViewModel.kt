package com.example.githubapp.domain.view_models

import android.security.keystore.UserNotAuthenticatedException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.R
import com.example.githubapp.domain.models.Repo
import com.example.githubapp.domain.use_cases.GetStringFromResourcesUseCase
import com.example.githubapp.domain.use_cases.GetTokenUseCase
import com.example.githubapp.domain.use_cases.api_use_cases.GetListReposUseCase
import com.example.githubapp.domain.utils.ConnectionErrorException
import com.example.githubapp.domain.utils.ServerNotRespondingException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoViewModel
@Inject
constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val getListReposUseCase: GetListReposUseCase,
    getStringFromResourcesUseCase: GetStringFromResourcesUseCase
) : ViewModel() {
    private val _serverNotRespondingMessage = getStringFromResourcesUseCase.execute(R.string.text_exception_server_not_responding)
    private val _internetConnectionMessage = getStringFromResourcesUseCase.execute(R.string.text_exception_check_internet_connection)
    private val _unknownError = getStringFromResourcesUseCase.execute(R.string.text_exception_cannot_open)

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    init {
        loadRepoList()
    }

    fun loadRepoList() {
        viewModelScope.launch {
            _state.postValue(State.Loading)

            try {
                val list = getListReposUseCase.execute(getTokenUseCase.execute()!!)
                if (list.isEmpty()) {
                    _state.postValue(State.Empty)
                    return@launch
                }

                _state.postValue(State.Loaded(list))
            } catch (ex: ServerNotRespondingException) {
                _state.postValue(State.Error(_serverNotRespondingMessage))
            } catch (ex: ConnectionErrorException) {
                _state.postValue(State.Error(_internetConnectionMessage))
            } catch (ex: UserNotAuthenticatedException) {
                _state.postValue(State.Error(ex.message.toString()))
            } catch (ex: Exception) {
                _state.postValue(State.Error("${_unknownError}\n${ex.message.toString()}"))
            }
        }
    }

    sealed interface State {
        object Loading : State
        data class Loaded(val repos: List<Repo>) : State
        data class Error(val error: String) : State
        object ConnectionError : State
        object Empty : State
    }
}
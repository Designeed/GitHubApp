package com.example.githubapp.domain.view_models

import android.security.keystore.UserNotAuthenticatedException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.R
import com.example.githubapp.domain.repository.AppRepository
import com.example.githubapp.domain.use_cases.GetStringFromResourcesUseCase
import com.example.githubapp.domain.use_cases.GetTokenUseCase
import com.example.githubapp.domain.use_cases.SaveTokenUseCase
import com.example.githubapp.domain.utils.ConnectionErrorException
import com.example.githubapp.domain.utils.ServerNotRespondingException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val appRepository: AppRepository,
    private val getStringFromResourcesUseCase: GetStringFromResourcesUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {
    private val _emptyTokenMessage = getStringFromResourcesUseCase.execute(R.string.text_exception_empty_field)
    private val _wrongTokenMessage = getStringFromResourcesUseCase.execute(R.string.text_exception_wrong_token)

    val token: MutableLiveData<String> = MutableLiveData(null)

    private val _state: MutableLiveData<State> = MutableLiveData(State.Idle)
    val state: LiveData<State>
        get() = _state

    private val _actions: Channel<Action> = Channel(Channel.BUFFERED)
    val actions: Flow<Action>
        get() = _actions.receiveAsFlow()

    init {
        viewModelScope.launch {
            val savedToken = getTokenUseCase.execute()
            if (savedToken.isNullOrBlank())
                return@launch

            authorizeByToken(savedToken)
        }
    }

    fun onPressButton() {
        viewModelScope.launch{
            val currentToken = token.value
            if (currentToken.isNullOrBlank()) {
                _state.postValue(State.InvalidInput(_emptyTokenMessage))
                return@launch
            }

            authorizeByToken(currentToken)
        }
    }

    private suspend fun authorizeByToken(token: String) {
        _state.postValue(State.Loading)
        try {
            appRepository.signIn(token)
            saveTokenUseCase.execute(token)

            _state.postValue(State.Idle)
            _actions.send(Action.RouteToDetail)
        } catch (ex: UserNotAuthenticatedException) {
            _state.postValue(State.InvalidInput(_wrongTokenMessage))
        } catch (ex: ServerNotRespondingException) {
            _state.postValue(State.Idle)
            _actions.send(Action.ShowError(ex.message.toString()))
        } catch (ex: ConnectionErrorException) {
            _state.postValue(State.Idle)
            _actions.send(Action.ShowError(ex.message.toString()))
        } catch (ex: Exception) {
            _state.postValue(State.Idle)
            _actions.send(Action.ShowError(ex.message.toString()))
        }
    }

    sealed interface State {
        object Idle : State
        object Loading : State
        data class InvalidInput(val reason: String) : State
    }

    sealed interface Action {
        data class ShowError(val message: String) : Action
        object RouteToDetail : Action
    }
}
package com.example.githubapp.domain.utils

import android.security.keystore.UserNotAuthenticatedException
import com.example.githubapp.R
import com.example.githubapp.domain.use_cases.GetStringFromResourcesUseCase
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

suspend fun <T> retrofitWrapException(request: suspend () -> T) : T {
    return try {
        request()
    } catch (ex: SocketTimeoutException) {
        throw ServerNotRespondingException(ex)
    } catch (ex: HttpException) {
        throw createServerException(ex)
    } catch (ex:IOException) {
        throw ConnectionErrorException(ex)
    }
}

private fun createServerException(entryException: HttpException) : Exception {
    when(entryException.code()) {
        401 -> throw UserNotAuthenticatedException()
        else -> throw entryException
    }
}

class ServerNotRespondingException : Exception {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String, cause: Throwable) : super(message, cause)
}

class ConnectionErrorException: Exception {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String, cause: Throwable) : super(message, cause)
}


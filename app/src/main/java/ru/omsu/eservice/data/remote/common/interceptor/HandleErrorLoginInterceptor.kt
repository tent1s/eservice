package ru.omsu.eservice.data.remote.common.interceptor

import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import ru.omsu.eservice.domain.repository.SessionRepository
import ru.omsu.eservice.ui.screen.Screens.splashScreen

class HandleErrorLoginInterceptor(
    private val router: Router,
    private val session: SessionRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return when {
            response.request.url.toString() == "https://eservice.omsu.ru/dasext/login?error" -> {
                response
                    .newBuilder()
                    .code(400)
                    .build()
            }
            response.request.url.toString() == "https://eservice.omsu.ru/dasext/login"
                    && !response.request.url.toString().contains("?error") -> {
                if (session.hasSession()) {
                    router.newRootScreen(splashScreen())
                    GlobalScope.launch(Dispatchers.IO) {
                        session.clear()
                    }
                    response
                        .newBuilder()
                        .code(401)
                        .build()
                } else {
                    response
                }
            }
            else -> response
        }
    }
}
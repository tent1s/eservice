package ru.omsu.eservice.data.remote.common.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HandleErrorLoginInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return if (response.request.url.toString() == "https://eservice.omsu.ru/dasext/login?error") {
            response
                .newBuilder()
                .code(400)
                .build()
        } else {
            response
        }
    }
}
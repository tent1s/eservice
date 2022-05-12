package ru.omsu.eservice.data.remote.common.interceptor

import okhttp3.Interceptor
import okhttp3.Response


class DynamicUrlInterceptor : Interceptor {

    companion object {
        private const val REDIRECT_URL_HEADER = "LocationRedirect"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val hasUrl = !request.headers[REDIRECT_URL_HEADER].isNullOrEmpty()

        return if (hasUrl) {
            chain.proceed(
                request.newBuilder()
                    .url(chain.proceed(request).headers[REDIRECT_URL_HEADER]!!)
                    .build()
            )
        } else {
            chain.proceed(request)
        }
    }
}
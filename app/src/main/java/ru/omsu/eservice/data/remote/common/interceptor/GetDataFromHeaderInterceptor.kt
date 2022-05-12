package ru.omsu.eservice.data.remote.common.interceptor

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class GetDataFromHeaderInterceptor : Interceptor {

    companion object {
        private const val CSRF_TOKEN_HEADER_NAME = "X-CSRF-TOKEN"
        private const val LOCATION_REDIRECT_URL = "Location"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val hasCsrfToken = !response.headers[CSRF_TOKEN_HEADER_NAME].isNullOrEmpty()
        val hasLocationUrl = !response.headers[LOCATION_REDIRECT_URL].isNullOrEmpty()
        return when {
            hasCsrfToken -> {
                val body =
                    "{ \"csrf\":${
                        response.headers[CSRF_TOKEN_HEADER_NAME]
                    } }".toResponseBody(
                        "text/plain".toMediaType()
                    )
                response
                    .newBuilder()
                    .code(200)
                    .body(body)
                    .build()
            }
            hasLocationUrl -> {
                val body =
                    "{ \"url\":${
                        response.headers[LOCATION_REDIRECT_URL]
                    } }".toResponseBody(
                        "text/plain".toMediaType()
                    )
                response
                    .newBuilder()
                    .code(200)
                    .body(body)
                    .build()
            }
            else -> response
        }
    }
}

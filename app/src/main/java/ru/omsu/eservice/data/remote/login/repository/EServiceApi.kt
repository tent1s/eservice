package ru.omsu.eservice.data.remote.login.repository

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.omsu.eservice.data.remote.login.entity.CsrfTokenNetwork
import ru.omsu.eservice.utils.Either
import ru.omsu.eservice.utils.ErrorReason

interface EServiceApi {

    @GET("/dasext/login")
    suspend fun getCsrfToken(): Either<ErrorReason, CsrfTokenNetwork>

    @POST("dasext/login.do")
    suspend fun login(
        @Query("j_username") login: String,
        @Query("j_password") password: String,
        @Query("_csrf") csrf: String
    ): Either<ErrorReason, Unit>


    @GET("/sinfo/backend/j_oauth_check?client_name=DasOAuth2Client&code=3c454S&state=72050baf79")
    suspend fun loginBackend(): Either<ErrorReason, Unit>
}
package ru.omsu.eservice.data.remote.login.repository

import retrofit2.http.*
import ru.omsu.eservice.data.remote.login.entity.CsrfTokenNetwork
import ru.omsu.eservice.data.remote.login.entity.EducationGroupNetwork
import ru.omsu.eservice.data.remote.login.entity.UrlNetwork
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


    @GET("/sinfo/backend/")
    suspend fun getUrlForAuthEducationCard(): Either<ErrorReason, UrlNetwork>

    @GET("")
    suspend fun goToCustomUrl(
        @Header("LocationRedirect") url: String
    ) : Either<ErrorReason, UrlNetwork>

    @Headers("CONNECT_TIMEOUT:120000", "READ_TIMEOUT:120000", "WRITE_TIMEOUT:120000")
    @GET("sinfo/backend/myStudents")
    suspend fun getEducationCard(): Either<ErrorReason, List<EducationGroupNetwork>>
}
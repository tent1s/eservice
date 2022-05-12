package ru.omsu.eservice.data.remote.login.repository

import ru.omsu.eservice.domain.repository.EducationCardRepository
import ru.omsu.eservice.utils.Either
import ru.omsu.eservice.utils.ErrorReason
import ru.omsu.eservice.utils.map
import javax.inject.Inject

class EducationCardRepositoryImp @Inject constructor(
    private val EServiceApi: EServiceApi
) : EducationCardRepository {
    override suspend fun getToken(): Either<ErrorReason, Unit> {
        val url = EServiceApi.getUrlForAuthEducationCard()
        if (url is Either.Success) {
            val newUrl = EServiceApi.goToCustomUrl( url.data.url!! )
            if (newUrl is Either.Success) {
                return EServiceApi.goToCustomUrl( newUrl.data.url!! ).map {}
            }
        }
        return url.map {}
    }

}
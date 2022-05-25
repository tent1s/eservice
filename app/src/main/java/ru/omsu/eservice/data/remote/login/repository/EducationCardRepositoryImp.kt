package ru.omsu.eservice.data.remote.login.repository

import ru.omsu.eservice.domain.model.EducationGroupUi
import ru.omsu.eservice.domain.model.mapper.EducationCardMapper
import ru.omsu.eservice.domain.repository.EducationCardRepository
import ru.omsu.eservice.utils.Either
import ru.omsu.eservice.utils.ErrorReason
import ru.omsu.eservice.utils.map
import javax.inject.Inject

class EducationCardRepositoryImp @Inject constructor(
    private val EServiceApi: EServiceApi,
    private val educationCardMapper: EducationCardMapper
) : EducationCardRepository {
    override suspend fun authAndGet(): Either<ErrorReason, List<EducationGroupUi>> {
        val url = EServiceApi.getUrlForAuthEducationCard()
        if (url is Either.Success) {
            if (url.data.url == "https://eservice.omsu.ru/sinfo/index.html")
                return educationCard()
            val newUrl = EServiceApi.goToCustomUrl(url.data.url!!)
            if (newUrl is Either.Success) {
                if (newUrl.data.url == "https://eservice.omsu.ru/sinfo/index.html")
                    return educationCard()
            }
        }
        return educationCard()
    }

    override suspend fun moreInformationOrder(id: Int): Either<ErrorReason, String> =
        EServiceApi.moreInformationOrder(id)

    suspend fun educationCard(): Either<ErrorReason, List<EducationGroupUi>> =
        EServiceApi.getEducationCard().map { list ->
            list.map { educationCardMapper.map(it) }
        }


}
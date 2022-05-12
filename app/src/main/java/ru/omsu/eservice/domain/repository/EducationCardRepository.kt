package ru.omsu.eservice.domain.repository

import ru.omsu.eservice.domain.model.EducationGroupUi
import ru.omsu.eservice.utils.Either
import ru.omsu.eservice.utils.ErrorReason

interface EducationCardRepository {
    suspend fun authAndGet(): Either<ErrorReason, List<EducationGroupUi>>
}
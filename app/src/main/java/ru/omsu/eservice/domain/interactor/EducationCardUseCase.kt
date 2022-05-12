package ru.omsu.eservice.domain.interactor

import ru.omsu.eservice.domain.repository.EducationCardRepository
import javax.inject.Inject

class EducationCardUseCase @Inject constructor(
    private val educationCardRepository: EducationCardRepository
) {
    suspend fun educationCard() = educationCardRepository.authAndGet()

}
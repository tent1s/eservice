package ru.omsu.eservice.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.omsu.eservice.data.device.database.EducationCardDao
import ru.omsu.eservice.data.device.database.EducationCardData
import ru.omsu.eservice.domain.interactor.EducationCardUseCase
import ru.omsu.eservice.domain.model.EducationGroupUi
import ru.omsu.eservice.ui.utils.sendNotification

@HiltWorker
class CheckGradeWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val educationCardDao: EducationCardDao,
    private val educationCardUseCase: EducationCardUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        return@withContext try {
            educationCardUseCase.educationCard().process(
                {
                    return@process Result.failure()
                },
                {
                    if (hasNewGrade(it)) {
                        applicationContext.applicationContext.sendNotification()
                    }
                    educationCardDao.insert(it.map { item ->
                        EducationCardData(item.summary?.group.orEmpty(), item)
                    })
                    throw Exception("success")
                }
            )

            Result.failure()
        } catch (error: Throwable) {
            Result.success()
        }
    }


    private fun hasNewGrade(fromServer: List<EducationGroupUi>): Boolean {
        val fromDatabase = educationCardDao.getAll()
        for (i in fromServer.indices) {
            for (j in fromDatabase.indices) {
                if (fromServer[i].summary?.group == fromDatabase[j].groupName) {
                    if (fromServer[i].sessions != fromDatabase[j].educationGroupUi.sessions) {
                        return true
                    }
                }
            }
        }
        return false
    }

}
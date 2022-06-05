package ru.omsu.eservice.data.device

import android.content.Context
import android.content.SharedPreferences
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import ru.omsu.eservice.data.device.database.EducationCardDao
import ru.omsu.eservice.data.worker.CheckGradeWorker
import ru.omsu.eservice.domain.repository.SessionRepository
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SessionRepositoryImp @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val cookieManager: CookieManager,
    private val educationCardDao: EducationCardDao,
    private val context: Context
) : SessionRepository {

    companion object {
        const val SESSION_FLAG = "SESSION_FLAG"
    }

    override fun hasSession(): Boolean =
        sharedPreferences.getBoolean(SESSION_FLAG, false)


    override suspend fun clear() {
        cookieManager.cookieStore.removeAll()
        sharedPreferences.edit().apply {
            remove(SESSION_FLAG)
            apply()
        }
        educationCardDao.clear()
        WorkManager.getInstance(context).cancelAllWorkByTag("e-service-grade-check")
    }

    override fun newSessionBeCreated() {
        sharedPreferences.edit().putBoolean(SESSION_FLAG, true).apply()
        val myWorkRequest =
            PeriodicWorkRequest.Builder(CheckGradeWorker::class.java, 12, TimeUnit.HOURS)
                .addTag("e-service-grade-check")
                .build()
        WorkManager.getInstance(context).enqueue(myWorkRequest)
    }

}
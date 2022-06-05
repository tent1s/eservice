package ru.omsu.eservice.data.device

import android.content.SharedPreferences
import net.gotev.cookiestore.removeAll
import ru.omsu.eservice.domain.repository.SessionRepository
import javax.inject.Inject
import java.net.CookieManager

class SessionRepositoryImp @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val cookieManager: CookieManager
) : SessionRepository {

    companion object {
        const val SESSION_FLAG = "SESSION_FLAG"
    }

    override fun hasSession(): Boolean =
        sharedPreferences.getBoolean(SESSION_FLAG, false)


    override fun clear() {
        cookieManager.cookieStore.removeAll()
        sharedPreferences.edit().apply {
            remove(SESSION_FLAG)
            apply()
        }
    }

    override fun newSessionBeCreated() {
        sharedPreferences.edit().putBoolean(SESSION_FLAG, true).apply()
    }

}
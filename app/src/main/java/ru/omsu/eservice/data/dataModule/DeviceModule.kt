package ru.omsu.eservice.data.dataModule

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.gotev.cookiestore.SharedPreferencesCookieStore
import java.net.CookieManager
import java.net.CookiePolicy
import java.security.SecureRandom
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager

@Module
@InstallIn(SingletonComponent::class)
class DeviceModule {

    @Singleton
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Singleton
    @Provides
    fun provideSslContext(trustAllCerts: Array<TrustManager>): SSLContext {
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        return sslContext
    }

    @Singleton
    @Provides
    fun provideCookieManager(context: Context): CookieManager = CookieManager(
        SharedPreferencesCookieStore(name = "myCookies", context = context),
        CookiePolicy.ACCEPT_ALL
    )

}
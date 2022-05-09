package com.example.testapplt.data.dataModule

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.testapplt.BuildConfig
import com.example.testapplt.data.remote.common.ResultCallAdapterFactory
import com.example.testapplt.data.remote.common.interceptor.GetDataFromHeaderInterceptor
import com.example.testapplt.data.remote.login.repository.EServiceApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.gotev.cookiestore.SharedPreferencesCookieStore
import net.gotev.cookiestore.okhttp.JavaNetCookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun provideOkHttpClient(
        cookieManager: CookieManager,
        @Named("GetDataFromHeaderInterceptor") getDataFromHeaderInterceptor: Interceptor,
        sslContext: SSLContext,
        trustAllCerts: Array<TrustManager>
    ) =
        OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(cookieManager))
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level =
                        HttpLoggingInterceptor.Level.BODY
                })
            .addInterceptor(getDataFromHeaderInterceptor)
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            .build()

    @Singleton
    @Provides
    fun retrofit(moshi: Moshi, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    moshi
                ).asLenient()
            )
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .client(client)
            .baseUrl(BuildConfig.apiUrl)
            .build()


    @Singleton
    @Provides
    fun provideGoogleBookApi(
        retrofit: Retrofit
    ): EServiceApi = retrofit.create(EServiceApi::class.java)

    @Singleton
    @Provides
    fun provideCookieManager(context: Context): CookieManager = CookieManager(
        SharedPreferencesCookieStore(name = "myCookies", context = context),
        CookiePolicy.ACCEPT_ALL
    )

    @Named("GetDataFromHeaderInterceptor")
    @Singleton
    @Provides
    fun provideGetDataFromHeaderInterceptor(): Interceptor =
        GetDataFromHeaderInterceptor()

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
    fun provideTrustAllCerts(): Array<TrustManager> = arrayOf(
        @SuppressLint("CustomX509TrustManager")
        object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {}
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    )

}
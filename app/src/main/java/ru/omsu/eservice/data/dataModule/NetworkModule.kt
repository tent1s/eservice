package ru.omsu.eservice.data.dataModule

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
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
import ru.omsu.eservice.BuildConfig
import ru.omsu.eservice.data.remote.common.ResultCallAdapterFactory
import ru.omsu.eservice.data.remote.common.interceptor.DynamicUrlInterceptor
import ru.omsu.eservice.data.remote.common.interceptor.GetDataFromHeaderInterceptor
import ru.omsu.eservice.data.remote.common.interceptor.HandleErrorLoginInterceptor
import ru.omsu.eservice.data.remote.login.repository.EServiceApi
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
        @Named("HandleErrorLoginInterceptor") handleErrorLoginInterceptor: Interceptor,
        @Named("DynamicUrlInterceptor") dynamicUrlInterceptor: Interceptor,
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
            .addInterceptor(handleErrorLoginInterceptor)
            .addInterceptor(dynamicUrlInterceptor)
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
    fun provideEServiceApi(
        retrofit: Retrofit
    ): EServiceApi = retrofit.create(EServiceApi::class.java)

    @Named("GetDataFromHeaderInterceptor")
    @Singleton
    @Provides
    fun provideGetDataFromHeaderInterceptor(): Interceptor =
        GetDataFromHeaderInterceptor()

    @Named("HandleErrorLoginInterceptor")
    @Singleton
    @Provides
    fun provideHandleErrorLoginInterceptor(): Interceptor =
        HandleErrorLoginInterceptor()

    @Named("DynamicUrlInterceptor")
    @Singleton
    @Provides
    fun provideDynamicUrlInterceptor(): Interceptor =
        DynamicUrlInterceptor()


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
package com.example.githubgraphql.data.interceptor

import com.example.githubgraphql.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationHeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val request = original.newBuilder()
        request.addHeader("Authorization", "Bearer ${BuildConfig.ACCESS_TOKEN}")
        return chain.proceed(request.build())
    }
}

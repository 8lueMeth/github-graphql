package com.example.githubgraphql.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationHeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
        request.addHeader("Authorization", "Bearer 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")
        return chain.proceed(request.build())
    }
}

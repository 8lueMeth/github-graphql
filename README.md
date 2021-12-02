# GithubGraphQL
Paging 3 with Jetpack Compose Sample Application Using Github GraphQL Api

## About
An Offline-First sample application which loads a user's info and repositories then shows the result in two diffrent tabs of a HorizontalPager.

It loads the data from the internet using [`apollo-android`](https://github.com/apollographql/apollo-android) and cachesh the result in a local `room` database. in this way the local database acts as our Single Source of Truth (SSOT).

## Usage
In order to make this application able to retrieve responses from github api you need to generate a github access token and place it inside [`AuthorizationHeaderInterceptor`](https://github.com/8lueMeth/GithubGraphQL/blob/master/app/src/main/java/com/example/githubgraphql/data/interceptor/AuthorizationHeaderInterceptor.kt) in `request.addHeader` after `Bearer`.

```
class AuthorizationHeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
        request.addHeader("Authorization", "Bearer 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")
        return chain.proceed(request.build())
    }
}
```

## Features & Libraries
* Kotlin
* MVVM
* Repository
* Apollo Android
* Paging 3 with RemoteMediator
* NetworkBoundResource (with Flow)
* Hilt
* Jetpack Compose
* Room
* Offline-First / Local Caching
* Flow
* Navigation
* Coil
* Accompanist SwipeToRefresh and Pager

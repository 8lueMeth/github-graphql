# GithubGraphQL
Paging 3 with Jetpack Compose Sample Application Using Github GraphQL Api

## About
An Offline-First sample application which loads a user's info and repositories then shows the result in two diffrent tabs of a HorizontalPager.

It loads the data from the internet using [`apollo-android`](https://github.com/apollographql/apollo-android) and cachesh the result in a local `room` database. in this way the local database acts as our Single Source of Truth (SSOT).

## Usage
In order to make this application able to retrieve responses from github api you need to generate a github access token, create a text file named `keystore.properties` like below.

```
ACCESS_TOKEN = "your api access token"
```
paste your access token as the value of `ACCESS_TOKEN` key.

then copy this file into the root folder of this project. the [`AuthorizationInterceptor`](https://github.com/8lueMeth/GithubGraphQL/blob/master/app/src/main/java/com/example/githubgraphql/data/interceptor/AuthorizationHeaderInterceptor.kt) will read the access token value from generated BuildConfig file.

```
class AuthorizationHeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val request = original.newBuilder()
        request.addHeader("Authorization", "Bearer ${BuildConfig.ACCESS_TOKEN}")
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

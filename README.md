# GithubGraphQL
Paging 3 with Jetpack Compose Sample Application Using Github GraphQL Api

## About
An Offline-First sample application which loads a user's info and repositories then shows the result in two diffrent tabs of a HorizontalPager.

It loads the data from the internet using [`apollo-android`](https://github.com/apollographql/apollo-android) and cachesh the result in a local `room` database. in this way the local database acts as our Single Source of Truth (SSOT).

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

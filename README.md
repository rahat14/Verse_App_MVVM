# Verse_App_MVVM
 A Test App Featuring MVVM,Hilt,Remote Mediator,Room,Navigation Component,Retrofit
# Tech stack & Open-source libraries
- 100% [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- JetPack🚀
  - ViewBinding - View binding is a feature that allows you to more easily write code that interacts with views.
  - LiveData With Flow - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Hilt -To simplify Dagger-related infrastructure for Android apps.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Gson](https://github.com/google/gson) - Gson is a Java library that can be used to convert Java Objects into their JSON representation.
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines,provides `runBlocking` coroutine builder used in tests
for Android.

## Architecture
- MVVM Architecture (View - ViewBinding - ViewModel - Model)
- Repository pattern

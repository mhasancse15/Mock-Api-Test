object Dependencies {

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val convertor = "com.squareup.retrofit2:converter-gson:${Versions.gson}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    }

    object Coil {
        const val coilKt= "io.coil-kt:coil:${Versions.coil}"
    }

    object Hilt{
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        const val hiltNavigation = "androidx.hilt:hilt-navigation-fragment:${Versions.hiltNavigation}"
    }


    object Nevigation{
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}"
        const val hinavigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}"
    }

    object Coroutine{
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Paging {
        const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
    }

    object Timber {
        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    }

    object LegacySupport{
        const val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.support}"
    }

    object Lifecycle{
        const val lifecycleViewModel= "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
        const val lifecycleLivedata= "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}"
        const val lifecycleViewModelSavedState= "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle_version}"
    }
}
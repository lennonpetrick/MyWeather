package com.test.myweather.di

import android.content.Context
import android.content.SharedPreferences
import android.location.LocationManager
import com.test.myweather.BuildConfig
import com.test.myweather.WeatherContract
import com.test.myweather.WeatherPresenter
import com.test.myweather.data.repository.WeatherRepositoryImpl
import com.test.myweather.data.repository.datasource.WeatherDataSource
import com.test.myweather.data.repository.datasource.local.LocalWeatherDataSource
import com.test.myweather.data.repository.datasource.remote.CloudWeatherDataSource
import com.test.myweather.domain.WeatherRepository
import com.test.myweather.domain.usecase.WeatherUseCase
import com.test.myweather.domain.usecase.WeatherUseCaseImpl
import com.test.myweather.shared.ApiHelper
import com.test.myweather.shared.LocationHelper
import com.test.myweather.shared.NetworkInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import javax.inject.Inject
import javax.inject.Named

@Module
class WeatherModule(private val context: Context) {

    @Provides
    fun view(): WeatherContract.View {
        return context as WeatherContract.View
    }

    @Provides
    @Inject
    fun presenter(
            view: WeatherContract.View,
            useCase: WeatherUseCase
    ): WeatherContract.Presenter {
        return WeatherPresenter(view, useCase)
    }

    @Provides
    @Inject
    fun useCase(repository: WeatherRepository): WeatherUseCase {
        return WeatherUseCaseImpl(repository)
    }

    @Provides
    @Inject
    fun repository(
            @Named("remote") cloudDataSource: WeatherDataSource,
            @Named("local") localDataSource: WeatherDataSource
    ): WeatherRepository {
        return WeatherRepositoryImpl.getInstance(cloudDataSource, localDataSource)
    }

    @Provides
    @Inject
    @Named("remote")
    fun cloudDataSource(interceptor: Interceptor): WeatherDataSource {
        return CloudWeatherDataSource(ApiHelper.getInstance(BuildConfig.TOKEN, interceptor))
    }

    @Provides
    fun interceptor(): Interceptor {
        return NetworkInterceptor(context)
    }

    @Provides
    @Inject
    @Named("local")
    fun localDataSource(sharedPreferences: SharedPreferences): WeatherDataSource {
        return LocalWeatherDataSource(sharedPreferences)
    }

    @Provides
    fun sharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(
                BuildConfig.APPLICATION_ID,
                Context.MODE_PRIVATE)
    }

    @Provides
    fun locationHelper(): LocationHelper {
        return LocationHelper(context.getSystemService(Context.LOCATION_SERVICE)
                as LocationManager)
    }
}
package com.test.myweather

import com.test.myweather.domain.usecase.WeatherUseCase

class WeatherPresenter(
        private var view: WeatherContract.View?,
        private var useCase: WeatherUseCase?
) : WeatherContract.Presenter {

    override fun destroy() {
        view = null
        useCase = null
    }

}
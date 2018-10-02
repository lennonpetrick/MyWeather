package com.test.myweather

import com.test.myweather.domain.models.City
import com.test.myweather.domain.usecase.WeatherUseCase
import com.test.myweather.shared.WeatherThemeHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.math.roundToInt

class WeatherPresenter(
        private var view: WeatherContract.View?,
        private var useCase: WeatherUseCase?
) : WeatherContract.Presenter {

    private val disposable = CompositeDisposable()

    override fun destroy() {
        disposable.clear()
        useCase = null
        view = null
    }

    override fun fetchCityWeather(city: String) {
        subscribeToWeather(useCase!!.getWeather(city))
    }

    override fun fetchCurrentWeather(lon: Double, lat: Double) {
        subscribeToWeather(useCase!!.getWeather(lon, lat))
    }

    override fun refresh() {
        subscribeToWeather(useCase!!.refresh())
    }

    private fun subscribeToWeather(observable: Observable<City>) {
        view?.displayLoading()
        disposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {
                    view?.dismissLoading()
                    loadInfoOnScreen(it)
                }, {
                    view?.dismissLoading()
                    view?.showError(it.message)
                    it.printStackTrace()
                }, {
                    view?.dismissLoading()
                }))
    }

    private fun loadInfoOnScreen(city: City) {
        view?.apply {
            city.weather?.let {
                val weatherTheme = WeatherThemeHelper.getInstance()
                this.setWeatherBackground(
                        weatherTheme.getBackground(it.icon),
                        weatherTheme.getBackgroundColor(it.icon)
                )
                this.setWeatherIcon(weatherTheme.getIcon(it.icon))
                this.setWeatherDescription(it.description)
            }

            var cityName = city.name
            city.country?.let {
                cityName = "$cityName, ${it.country}"
            }

            this.setCityName(cityName)
            this.setWeatherTemperature(city.main?.temperature!!.roundToInt())

            val calendar = GregorianCalendar()
            this.setDateInfo(
                    getDayOfWeekText(calendar.get(Calendar.DAY_OF_WEEK)),
                    getMonthText(calendar.get(Calendar.MONTH)),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.HOUR),
                    calendar.get(Calendar.MINUTE))
        }
    }

    private fun getDayOfWeekText(day: Int): String {
        return when(day) {
            Calendar.SUNDAY -> "Sunday"
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            else -> ""
        }
    }

    private fun getMonthText(month: Int): String {
        return when(month) {
            Calendar.JANUARY -> "January"
            Calendar.FEBRUARY -> "February"
            Calendar.MARCH -> "March"
            Calendar.APRIL -> "April"
            Calendar.MAY -> "May"
            Calendar.JUNE -> "June"
            Calendar.JULY -> "July"
            Calendar.AUGUST -> "August"
            Calendar.SEPTEMBER -> "September"
            Calendar.OCTOBER -> "October"
            Calendar.NOVEMBER -> "November"
            Calendar.DECEMBER -> "December"
            else -> ""
        }
    }
}
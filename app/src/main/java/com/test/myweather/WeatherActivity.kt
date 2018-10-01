package com.test.myweather

import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.test.myweather.di.DaggerWeatherComponent
import com.test.myweather.di.WeatherModule
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.layout_weather_bottom.*
import kotlinx.android.synthetic.main.layout_weather_top.*
import java.util.*
import javax.inject.Inject

class WeatherActivity : AppCompatActivity(), WeatherContract.View {

    @Inject
    lateinit var presenter: WeatherContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        injectDependencies()
        setUpViews()
        presenter.fetchCityWeather("franca")
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun setWeatherBackground(drawable: Int, colorRes: Int) {
        window.statusBarColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getColor(colorRes)
        } else {
            resources.getColor(colorRes)
        }

        refreshLayout.setColorSchemeResources(colorRes)
        topContent.setBackgroundResource(drawable)
    }

    override fun setWeatherIcon(idRes: Int?) {
        idRes?.let { ivWeatherIcon.setImageResource(it) }
    }

    override fun setWeatherDescription(description: String?) {
        tvWeatherDescription.text = description
    }

    override fun setWeatherTemperature(temperature: Int) {
        tvTemperature.text = temperature.toString()
        tvTemperatureSymbol.text = getString(R.string.text_weather_screen_temperature_symbol)
    }

    override fun setCityName(name: String?) {
        tvCity.text = name
    }

    override fun setDateInfo(
            dayOfWeek: String, month: String, day: Int,
            hour: Int, minute: Int
    ) {
        tvDate.text = getString(R.string.text_weather_screen_date)
                .format(Locale.getDefault(), dayOfWeek, month, day, hour, minute)
    }

    override fun showError(error: String?) {
        Snackbar.make(
                refreshLayout,
                error ?: getText(R.string.unknown_error),
                Snackbar.LENGTH_LONG
        ).show()
    }

    override fun displayLoading() {
        refreshLayout.isRefreshing = true
    }

    override fun dismissLoading() {
        refreshLayout.isRefreshing = false
    }

    private fun injectDependencies() {
        DaggerWeatherComponent
                .builder()
                .weatherModule(WeatherModule(this))
                .build()
                .inject(this)
    }

    private fun setUpViews() {
        refreshLayout.apply {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener {
                presenter.refresh()
            }
        }
    }
}

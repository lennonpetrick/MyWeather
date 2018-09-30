package com.test.myweather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.test.myweather.di.DaggerWeatherComponent
import com.test.myweather.di.WeatherModule
import javax.inject.Inject

class WeatherActivity : AppCompatActivity(), WeatherContract.View {

    @Inject
    lateinit var presenter: WeatherContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        injectDependencies()
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    private fun injectDependencies() {
        DaggerWeatherComponent
                .builder()
                .weatherModule(WeatherModule(this))
                .build()
                .inject(this)
    }

}

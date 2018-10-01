package com.test.myweather

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
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
        handleIntent(intent)
    }

    override fun onResume() {
        presenter.fetchCityWeather("franca")
        super.onResume()
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_SEARCH) {
            val query = intent.getStringExtra(SearchManager.QUERY).trim()
            presenter.fetchCityWeather(query)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        return super.onCreateOptionsMenu(menu)
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
        setSupportActionBar(toolbar).apply {
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        refreshLayout.apply {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener {
                presenter.refresh()
            }
        }
    }
}

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
import com.test.myweather.shared.LocationHelper
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.layout_weather_bottom.*
import kotlinx.android.synthetic.main.layout_weather_top.*
import java.util.*
import javax.inject.Inject



class WeatherActivity : AppCompatActivity(), WeatherContract.View {

    @Inject
    lateinit var presenter: WeatherContract.Presenter

    @Inject
    lateinit var locationHelper: LocationHelper

    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private val ENABLE_LOCATION_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        injectDependencies()
        setUpViews()
        fetchByLocation()
    }

    override fun onResume() {
        presenter.refresh()
        super.onResume()
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleIntent(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ENABLE_LOCATION_REQUEST_CODE) {
            fetchByLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (locationHelper.gainedPermission(grantResults)) {
            fetchByLocation()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.isIconified = true
                searchView.clearFocus()
                searchItem.collapseActionView()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {return false}
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    @Suppress("DEPRECATION")
    override fun setWeatherBackground(drawable: Int, colorRes: Int) {
        window.statusBarColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getColor(colorRes)
        } else {
            resources.getColor(colorRes)
        }

        refreshLayout.setColorSchemeResources(colorRes)
        topContent.setBackgroundResource(drawable)
        toolbar.setBackgroundResource(android.R.color.transparent)
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
            setColorSchemeResources(R.color.colorAccent)
            setOnRefreshListener {
                presenter.refresh()
            }
        }
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_SEARCH) {
            val query = intent.getStringExtra(SearchManager.QUERY).trim()
            presenter.fetchCityWeather(query)
        }
    }

    private fun fetchByLocation() {
        if (!locationHelper.hasPermission(this)) {
            locationHelper.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        if (!locationHelper.isLocationAvailable(this, ENABLE_LOCATION_REQUEST_CODE))
            return

        locationHelper.requestLocation { lon, lat ->
            presenter.fetchCurrentWeather(lon, lat)
        }
    }
}

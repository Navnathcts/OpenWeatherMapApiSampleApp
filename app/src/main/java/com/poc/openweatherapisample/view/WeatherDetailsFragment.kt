package com.poc.openweatherapisample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.azhar.weatherapp.adapter.WeatherDataAdapter
import com.poc.openweatherapisample.R
import com.poc.openweatherapisample.util.CloudIconProvider
import com.poc.swipecarouselapp.data.ApiClient
import com.poc.swipecarouselapp.data.ApiHelper
import com.poc.swipecarouselapp.util.Status
import com.poc.swipecarouselapp.util.hideView
import com.poc.swipecarouselapp.util.showView
import kotlinx.android.synthetic.main.main_fragment.*

class WeatherDetailsFragment : Fragment() {
    private lateinit var viewModel: WeatherDetailsViewModel
    private var mainAdapter: WeatherDataAdapter? = null
    private lateinit var lat: String
    private lateinit var long: String
    private lateinit var cityName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this@WeatherDetailsFragment,
            ViewModelFactory(ApiHelper(ApiClient.apiService))
        ).get(WeatherDetailsViewModel::class.java)
        mainAdapter = WeatherDataAdapter()
        arguments?.apply {
            lat = getString(getString(R.string.key_latitude)) ?: ""
            long = getString(getString(R.string.key_longitude)) ?: ""
            cityName = getString(getString(R.string.key_city_name)) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvListWeather.setLayoutManager(
            LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        rvListWeather.setHasFixedSize(true)
        rvListWeather.setAdapter(mainAdapter)
        fetchCurrentDayWeatherInfo()
        fetchNext5DaysInformation()
        btnTryAgain?.setOnClickListener { fetchNext5DaysInformation() }
    }

    private fun fetchCurrentDayWeatherInfo() {
        currDayWeatherInfoShimmerFrame?.startShimmerAnimation()
        viewModel.getCurrentWeatherDataByLatLong(
            lat,
            long,
            getString(R.string.metric)
        ).observe(
            requireActivity(), Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        currDayWeatherInfoShimmerFrame?.hideView()
                        currDayWeatherInfoShimmerFrame?.stopShimmerAnimation()
                        cvCurrDayWeatherInfo?.showView()
                        it.data?.apply {
                            tvTemp.text = """${main?.temp.toString()} °C"""
                            tvHumidity.text = """${getString(R.string.humidity)} ${main?.humidity.toString()} %"""
                            tvWind.text = """${getString(R.string.wind)} ${wind?.speed?.toString()} m/s"""
                            tvLocation.text = cityName
                            if (weather.isNotEmpty()) {
                                weather.get(0).description.apply {
                                    tvCloudDescri.text = "${getString(R.string.humidity)} $this"
                                    iconTemp.setAnimation(CloudIconProvider.getCloudIcon(this))
                                }
                            }
                        }
                    }
                    Status.ERROR -> {
                        currDayWeatherInfoShimmerFrame?.hideView()
                        currDayWeatherInfoShimmerFrame?.stopShimmerAnimation()
                        cvCurrDayWeatherInfo?.showView()
                        tvLocation.text = it.message
                    }
                    Status.LOADING -> {
                        currDayWeatherInfoShimmerFrame?.showView()
                        currDayWeatherInfoShimmerFrame?.startShimmerAnimation()
                        cvCurrDayWeatherInfo?.hideView()
                    }
                }
            }

        )
    }

    private fun fetchNext5DaysInformation() {
        rvShimmerFrameLayout?.startShimmerAnimation()
        viewModel.getNextFiveDaysData(
            lat,
            long,
            "metric"
        ).observe(
            requireActivity(), Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        rvShimmerFrameLayout?.hideView()
                        rvShimmerFrameLayout?.stopShimmerAnimation()
                        rvListWeather?.showView()
                        llErrorView?.hideView()
                        mainAdapter?.setForeCastData(it.data)
                    }
                    Status.ERROR -> {
                        rvShimmerFrameLayout?.hideView()
                        rvShimmerFrameLayout?.stopShimmerAnimation()
                        rvListWeather?.hideView()
                        llErrorView?.showView()
                        tvErrorMsg?.text = it.message
                    }
                    Status.LOADING -> {
                        rvShimmerFrameLayout?.showView()
                        rvListWeather?.hideView()
                        llErrorView?.hideView()
                    }
                }
            }
        )
    }

}

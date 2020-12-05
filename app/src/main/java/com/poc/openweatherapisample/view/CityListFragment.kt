package com.poc.openweatherapisample.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.azhar.weatherapp.adapter.CityListAdapter
import com.poc.openweatherapisample.R
import com.poc.openweatherapisample.model.CityDetailModel
import com.poc.openweatherapisample.util.ACTION_REQUEST_CODE_FOR_MAP_ACTIVITY
import com.poc.openweatherapisample.util.KEY_CITY_LIST
import com.poc.openweatherapisample.util.SharedPrefUtility
import com.poc.swipecarouselapp.util.hideView
import com.poc.swipecarouselapp.util.showView
import com.shivtechs.maplocationpicker.LocationPickerActivity
import com.shivtechs.maplocationpicker.MapUtility
import kotlinx.android.synthetic.main.city_list_fragment.*

class CityListFragment : Fragment(), CityListAdapter.CityItemClickListener {

    private var cityListAdapter: CityListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityListAdapter = CityListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.city_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvLocationList?.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = cityListAdapter
        }
        btnAddCity?.setOnClickListener {
            val intent = Intent(requireActivity(), LocationPickerActivity::class.java)
            startActivityForResult(intent, ACTION_REQUEST_CODE_FOR_MAP_ACTIVITY)
        }
    }

    override fun onResume() {
        super.onResume()
        SharedPrefUtility.getHashMap(
            requireActivity(),
            KEY_CITY_LIST
        ).let {
            when (it?.size) {
                0 -> {
                    tvNoCityList.showView()
                    rvLocationList.hideView()
                }
                else -> {
                    tvNoCityList.hideView()
                    rvLocationList.showView()
                    cityListAdapter?.setCityList(it)
                }
            }
        }
    }

    override fun onCityItemClick(cityDetailModel: CityDetailModel?) {
        val data = bundleOf(
            getString(R.string.key_latitude) to cityDetailModel?.currentLongitude,
            getString(R.string.key_longitude) to cityDetailModel?.currentLatitude,
            getString(R.string.key_city_name) to cityDetailModel?.city
        )
        findNavController().navigate(
            R.id.action_cityListFragment_to_mainFragment,
            data
        )

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACTION_REQUEST_CODE_FOR_MAP_ACTIVITY) {
            try {
                if (data != null && data.getStringExtra(MapUtility.ADDRESS) != null) {
                    val currentLatitude = data.getDoubleExtra(MapUtility.LATITUDE, 0.0)
                    val currentLongitude = data.getDoubleExtra(MapUtility.LONGITUDE, 0.0)
                    val completeAddress = data.getBundleExtra("fullAddress")
                    val address = StringBuilder().append("\nCity: ")
                        .append(completeAddress.getString("city")).append("\nPostalcode: ")
                        .append(completeAddress.getString("postalcode")).append("\nState: ")
                        .append(completeAddress.getString("state")).append("\nCountry: ")
                        .append(completeAddress.getString("country")).toString()
                    val map = mutableMapOf<String?, CityDetailModel?>()
                    map.put(
                        address,
                        CityDetailModel().apply {
                            this.address = address
                            this.city =
                                completeAddress.getString("city") + ", " + completeAddress.getString(
                                    "country"
                                )
                            this.currentLatitude = currentLatitude.toString()
                            this.currentLongitude = currentLongitude.toString()
                        })
                    SharedPrefUtility.saveHashMap(requireActivity(), KEY_CITY_LIST, map)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}

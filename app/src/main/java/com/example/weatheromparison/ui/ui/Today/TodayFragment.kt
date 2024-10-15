package com.example.weatheromparison.ui.ui.fiveDays

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weatheromparison.databinding.FragmentTodayBinding
import com.example.weatheromparison.ui.entity.Temp
import com.example.weatheromparison.ui.ui.adapters.TodayAdapter
import com.example.weatheromparison.ui.ui.funGranted.isPermissionGranted

import com.example.weatheromparison.ui.ui.threeHours.ThreeHoursViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.launch
import kotlin.math.log

class TodayFragment : Fragment() {
    private lateinit var binding: FragmentTodayBinding
    private lateinit var fusedClient: FusedLocationProviderClient
    private var lon: Double = 50.15
    private var lat: Double = 53.20
    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        if (map.values.isNotEmpty() && map.values.all { it }) {
            startLocation()
        }
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            lon = p0.lastLocation?.longitude ?: 50.15
            lat = p0.lastLocation?.latitude ?: 53.20
            binding.imageButtonReboot.setOnClickListener {
                lon = p0.lastLocation?.longitude ?: 50.15
                lat = p0.lastLocation?.latitude ?: 53.20
                getAdapter()
            }
        }
    }

    private val viewModel: TodayViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayBinding.inflate(inflater, container, false)
        fusedClient = LocationServices.getFusedLocationProviderClient(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAdapter()
    }

    private fun getAdapter() {
        lifecycleScope.launch {
            Log.i("fh", "Загрузка1")
            val getWeather = viewModel.getTodaYWeather(lon, lat)
            Log.i("fh", "Загрузка2")
            binding.NameCity.text = getWeather.city.name
            val listWeatherToday = mutableListOf<Temp>()
            for (x in 0..8) {
                listWeatherToday.add(getWeather.list[x])
            }
            val listWeatherTodayAdapter = TodayAdapter(listWeatherToday)
            binding.recyclerViewToday.adapter = listWeatherTodayAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        fusedClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startLocation() {
        val request = LocationRequest.create()
            .setInterval(1_000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        fusedClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun checkPermissions() {
        val isAllGranted = REQUEST_PERMISSONS.all { premission ->
            ContextCompat.checkSelfPermission(
                requireContext().applicationContext,
                premission
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (isAllGranted) {
            startLocation()
        } else {
            launcher.launch(REQUEST_PERMISSONS)
        }
    }

    companion object {
        private val REQUEST_PERMISSONS: Array<String> = buildList {
            add(android.Manifest.permission.ACCESS_FINE_LOCATION)
            add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }.toTypedArray()
    }
}
package com.example.weatheromparison.ui.ui.threeHours

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
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


import com.example.weatheromparison.databinding.FragmentThreeHoursBinding


import com.example.weatheromparison.ui.ui.funGranted.isPermissionGranted
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.launch

class ThreeHoursFragment : Fragment() {
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
    private lateinit var binding: FragmentThreeHoursBinding

    private val viewModel: ThreeHoursViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThreeHoursBinding.inflate(inflater, container, false)
        fusedClient = LocationServices.getFusedLocationProviderClient(requireContext())
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAdapter()
    }

    private fun getAdapter() {
        lifecycleScope.launch {
            val listTempo = viewModel.getThreeHoursWeather(lon, lat)
            val tempo = listTempo.list[1]
            binding.NameCity.text = listTempo.city.name
            binding.dataAndTime.text = tempo.dt_txt
            binding.textTempo.text = tempo.main.temp.toString() + " °С"
            binding.textTempoFell.text = tempo.main.feels_like.toString() + " °С"
            binding.textTempoMax.text = tempo.main.temp_max.toString() + " °С"
            binding.textTempoMin.text = tempo.main.temp_min.toString() + " °С"
            binding.textCloudCover.text = tempo.weather[0].description
            binding.textSpeedWind.text = tempo.wind.speed.toString() + " м/с"
            when (tempo.wind.deg) {
                in 0..22 -> {
                    binding.textDirectionWind.text = "Северный"
                }

                in 23..67 -> {
                    binding.textDirectionWind.text = "Северно-Восточный"
                }

                in 67..112 -> {
                    binding.textDirectionWind.text = "Восточный"
                }

                in 112..158 -> {
                    binding.textDirectionWind.text = "Юго-Восточный"
                }

                in 158..202 -> {
                    binding.textDirectionWind.text = "Южный"
                }

                in 202..248 -> {
                    binding.textDirectionWind.text = "Юго-Западный"
                }

                in 248..292 -> {
                    binding.textDirectionWind.text = "Восточный"
                }

                in 292..338 -> {
                    binding.textDirectionWind.text = "Северо-Восточный"
                }

                in 338..360 -> {
                    binding.textDirectionWind.text = "Северный"
                }
            }
            binding.textMaxWind.text = tempo.wind.gust.toString() + " м/с"
            binding.textProbabilityOfPrecipitation.text = (tempo.pop * 100).toString() + " %"
            binding.textPercentageOfClouds.text = tempo.clouds.all.toString() + " %"
            binding.textVisibility.text = tempo.visibility.toString() + " м"

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
            Toast.makeText(
                requireContext().applicationContext,
                "Permission is  granted",
                Toast.LENGTH_SHORT
            ).show()
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
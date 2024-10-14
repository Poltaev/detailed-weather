package com.example.weatheromparison.ui.ui.threeHours

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import com.example.weatheromparison.databinding.FragmentThreeHoursBinding


import com.example.weatheromparison.ui.ui.funGranted.isPermissionGranted
import kotlinx.coroutines.launch

class ThreeHoursFragment : Fragment() {
    private lateinit var pLauncher: ActivityResultLauncher<String>

    private lateinit var binding: FragmentThreeHoursBinding

    private val viewModel: ThreeHoursViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThreeHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        lifecycleScope.launch {
            val listTempo = viewModel.getThreeHoursWeather(50.15, 53.20)
            val tempo = listTempo.list[1]
            binding.NameCity.text = listTempo.city.name
            binding.dataAndTime.text = tempo.dt_txt
            binding.textTempo.text = tempo.main.temp.toString() + " °С"
            binding.textTempoFell.text = tempo.main.feels_like.toString() + " °С"
            binding.textTempoMax.text = tempo.main.temp_max.toString() + " °С"
            binding.textTempoMin.text = tempo.main.temp_min.toString() + " °С"
            binding.textCloudCover.text = tempo.weather[0].description
            binding.textSpeedWind.text = tempo.wind.speed.toString() + " м/с"
            when(tempo.wind.deg) {
                in  0.. 22 -> {
                    binding.textDirectionWind.text = "Северный"
                }
                in  23.. 67 -> {
                    binding.textDirectionWind.text = "Северно-Восточный"
                }
                in  67.. 112 -> {
                    binding.textDirectionWind.text = "Восточный"
                }
                in  112.. 158 -> {
                    binding.textDirectionWind.text = "Юго-Восточный"
                }
                in  158.. 202 -> {
                    binding.textDirectionWind.text = "Южный"
                }
                in  202.. 248 -> {
                    binding.textDirectionWind.text = "Юго-Западный"
                }
                in  248.. 292 -> {
                    binding.textDirectionWind.text = "Восточный"
                }
                in  292.. 338 -> {
                    binding.textDirectionWind.text = "Северо-Восточный"
                }
                in  338.. 360 -> {
                    binding.textDirectionWind.text = "Северный"
                }
            }
            binding.textMaxWind.text = tempo.wind.gust.toString()+ " м/с"
            binding.textProbabilityOfPrecipitation.text = tempo.pop.toString() + " %"
            binding.textPercentageOfClouds.text = tempo.clouds.all.toString()  + " %"
            binding.textVisibility.text = tempo.visibility.toString()  + " м"

        }
    }

    private fun permissionListner() {
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(activity, " Permission is ${it}", Toast.LENGTH_LONG).show()
        }
    }
    private fun checkPermission () {
       if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
           permissionListner()
           pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
       }
    }


}
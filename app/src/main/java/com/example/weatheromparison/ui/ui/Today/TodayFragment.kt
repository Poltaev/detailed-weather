package com.example.weatheromparison.ui.ui.fiveDays

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weatheromparison.databinding.FragmentTodayBinding
import com.example.weatheromparison.ui.entity.Temp
import com.example.weatheromparison.ui.ui.adapters.TodayAdapter
import com.example.weatheromparison.ui.ui.funGranted.isPermissionGranted

import com.example.weatheromparison.ui.ui.threeHours.ThreeHoursViewModel
import kotlinx.coroutines.launch
import kotlin.math.log

class TodayFragment : Fragment() {
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentTodayBinding

    private val viewModel: TodayViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        Log.i("fh", "вью")
        lifecycleScope.launch {
            Log.i("fh", "Загрузка1")
            val getWeather = viewModel.getTodaYWeather(50.15, 53.20)
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

    private fun permissionListner() {
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(activity, " Permission is ${it}", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListner()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}
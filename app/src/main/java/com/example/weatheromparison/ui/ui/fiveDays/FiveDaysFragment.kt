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
import com.example.weatheromparison.databinding.FragmentFiveDaysBinding
import com.example.weatheromparison.ui.entity.Temp
import com.example.weatheromparison.ui.ui.adapters.FiveDayAdapter
import com.example.weatheromparison.ui.ui.adapters.TodayAdapter
import com.example.weatheromparison.ui.ui.funGranted.isPermissionGranted

import com.example.weatheromparison.ui.ui.threeHours.ThreeHoursViewModel
import kotlinx.coroutines.launch

class FiveDaysFragment : Fragment() {
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentFiveDaysBinding

    private val viewModel: FiveDaysViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFiveDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        lifecycleScope.launch {
            Log.i("fh", "Загрузка1")
            val getWeather = viewModel.getFiveDayWeather(50.15, 53.20)
            Log.i("fh", "Загрузка2")
            val listWeatherToday = mutableListOf<Temp>()
            binding.NameCity.text = getWeather.city.name
            for(x in 0 .. 39) {
                if (x % 2 == 0) {
                    listWeatherToday.add(getWeather.list[x])
                }
            }
            val listWeatherTodayAdapter = FiveDayAdapter(listWeatherToday)
            binding.recyclerViewFiveDays.adapter = listWeatherTodayAdapter
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
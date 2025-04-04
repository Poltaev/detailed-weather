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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weatheromparison.R


import com.example.weatheromparison.ui.ui.funGranted.isPermissionGranted
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.internal.wait

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
//            binding.imageButtonReboot.setOnClickListener {
//                lon = p0.lastLocation?.longitude ?: 50.15
//                lat = p0.lastLocation?.latitude ?: 53.20
//                getAdapter()
//            }
        }
    }
//    private lateinit var binding: FragmentThreeHoursBinding

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
        val view = ComposeView(requireContext())
        fusedClient = LocationServices.getFusedLocationProviderClient(requireContext())
        lifecycleScope.launch(Dispatchers.IO) {
            val threeHourWether = getThreeHourWeather()
            launch(Dispatchers.Main) {
                view.setContent {
                    threeHoursView(threeHourWether)
                }
            }
        }

        return view

    }

    @Composable
    fun threeHoursView(data: ThreeHoursWeather) {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(16F / 7F),
                Alignment.TopCenter

            ) {
                theTitlePicture(
                    modifire = Modifier
                        .padding(5.dp)
                )
                text(
                    modifire = Modifier
                        .align(Alignment.Center),
                    text = data.nameSity
                )
            }
            text(
                modifire = Modifier
                    .padding(
                        15.dp,
                        4.dp
                    ),
                text = data.data,
                fontSize = 28.sp
            )

            text(
                modifire = Modifier
                    .padding(
                        15.dp,
                        4.dp
                    ),
                text = data.thisT,
                fontSize = 38.sp,
                color = colorResource(R.color.teal_200)
            )
            text(
                modifire = Modifier
                    .padding(
                        15.dp,
                        4.dp
                    ),
                text = data.cloudCover,
                fontSize = 20.sp,
            )
            // Ощущается
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = "Ощущается:",
                    fontSize = 20.sp,
                )
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = data.tfeel,
                    fontSize = 20.sp,
                )
            }
            // T min
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = "T min:",
                    fontSize = 20.sp,
                    color = colorResource(R.color.teal_200)

                )
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = data.tmin,
                    fontSize = 20.sp,
                    color = colorResource(R.color.teal_200)
                )
            }
            // T max
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = "T max:",
                    fontSize = 20.sp,
                )
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = data.tmax,
                    fontSize = 20.sp,
                )
            }

            // Скорость ветра
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = "Скорость ветра:",
                    fontSize = 20.sp,
                    color = colorResource(R.color.teal_200)

                )
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = data.speedWind,
                    fontSize = 20.sp,
                    color = colorResource(R.color.teal_200)
                )
            }
            // Ветер
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = "Ветер:",
                    fontSize = 20.sp,
                )
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = data.wind,
                    fontSize = 20.sp,
                )
            }
            // Порывы ветра до:
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = "Порывы ветра до:",
                    fontSize = 20.sp,
                    color = colorResource(R.color.teal_200)

                )
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = data.gustsOfWind,
                    fontSize = 20.sp,
                    color = colorResource(R.color.teal_200)
                )
            }
            // Вероятность осадков
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = "Вероятность осадков:",
                    fontSize = 20.sp,
                )
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = data.precipitationProbability,
                    fontSize = 20.sp,
                )
            }
            // Процент облаков
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = "Процент облаков:",
                    fontSize = 20.sp,
                    color = colorResource(R.color.teal_200)

                )
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = data.percentageOfClouds,
                    fontSize = 20.sp,
                    color = colorResource(R.color.teal_200)
                )
            }
            // Видимость
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = "Видимость:",
                    fontSize = 20.sp,
                )
                text(
                    modifire = Modifier
                        .padding(
                            15.dp,
                            4.dp
                        ),
                    text = data.visibility,
                    fontSize = 20.sp,
                )
            }
        }
    }

    @Composable
    fun text(
        modifire: Modifier = Modifier,
        text: String = "Text text",
        fontSize: TextUnit = 30.sp,
        color: Color = Color.Unspecified
    ) {
        Text(
            text = text,
            modifier = modifire,
            color = color,
            fontSize = fontSize,
        )
    }

    @Composable
    fun theTitlePicture(
        modifire: Modifier = Modifier
    ) {
        Image(
            painter = painterResource(R.drawable.img),
            contentDescription = "Заглавный рисунок",
            modifier = modifire,
            contentScale = ContentScale.FillBounds,
            alignment = Alignment.TopStart
        )
    }

    private suspend fun getThreeHourWeather(): ThreeHoursWeather {

       val threeHoursWeather = lifecycleScope.async {
            val listTempo = viewModel.getThreeHoursWeather(lon, lat)
            val tempo = listTempo.list[1]
                var dataTime = ""
                dataTime += tempo.dt_txt[11]
                dataTime += tempo.dt_txt[12]
                dataTime += tempo.dt_txt[13]
                dataTime += tempo.dt_txt[14]
                dataTime += tempo.dt_txt[15]
                dataTime += tempo.dt_txt[10]
                dataTime += tempo.dt_txt[8]
                dataTime += tempo.dt_txt[9]
                dataTime += "."
                dataTime += tempo.dt_txt[5]
                dataTime += tempo.dt_txt[6]
            var firstLetter = tempo.weather[0].description.take(1).uppercase()
            var repleseLetter = tempo.weather[0].description.take(1)
            var cloudCover = tempo.weather[0].description.replaceFirst(repleseLetter, firstLetter, true)
            var wind = "Северный"
            when (tempo.wind.deg) {
                in 0..22 -> {
                    wind = "Северный"
                }

                in 23..67 -> {
                    wind = "Северно-Восточный"
                }

                in 67..112 -> {
                    wind = "Восточный"
                }

                in 112..158 -> {
                    wind = "Юго-Восточный"
                }

                in 158..202 -> {
                    wind = "Южный"
                }

                in 202..248 -> {
                    wind = "Юго-Западный"
                }

                in 248..292 -> {
                    wind = "Восточный"
                }

                in 292..338 -> {
                    wind = "Северо-Восточный"
                }

                in 338..360 -> {
                    wind = "Северный"
                }
            }
            ThreeHoursWeather(
                listTempo.city.name,
                dataTime,
                tempo.main.temp.toString() + " °С",
                cloudCover,
                tempo.main.feels_like.toString() + " °С",
                tempo.main.temp_min.toString() + " °С",
                tempo.main.temp_max.toString() + " °С",
                tempo.wind.speed.toString() + " м/с",
                wind,
                tempo.wind.gust.toString() + " м/с",
                (tempo.pop * 100).toString() + " %",
                tempo.clouds.all.toString() + " %",
                tempo.visibility.toString() + " м"
            )
        }
    val getThreeHoursWeather = threeHoursWeather.await()
    return getThreeHoursWeather
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
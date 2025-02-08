package com.example.weatheromparison.ui.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatheromparison.databinding.FiveDayItemBinding
import com.example.weatheromparison.databinding.TodayForItemBinding
import com.example.weatheromparison.ui.entity.Temp

class FiveDayAdapter (
    private val data: List<Temp>,
) :
    RecyclerView.Adapter<FiveDayViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FiveDayViewHolder {
        val binding = FiveDayItemBinding.inflate(LayoutInflater.from(parent.context))
        return FiveDayViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: FiveDayViewHolder, position: Int) {

        val item = data.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                var dataTime = ""
                dataTime += item.dt_txt[11]
                dataTime += item.dt_txt[12]
                dataTime += item.dt_txt[13]
                dataTime += item.dt_txt[14]
                dataTime += item.dt_txt[15]
                dataTime += item.dt_txt[10]
                dataTime += item.dt_txt[8]
                dataTime += item.dt_txt[9]
                dataTime += "."
                dataTime += item.dt_txt[5]
                dataTime += item.dt_txt[6]

                textDataAndTime.text = dataTime
                textTempo.text = item.main.temp.toString() + " °С"
                textTempoFell.text = item.main.feels_like.toString() + " °С"
                textTempoMax.text = item.main.temp_max.toString() + " °С"
                textTempoMin.text = item.main.temp_min.toString() + " °С"
                var firstLetter = item.weather[0].description.take(1).uppercase()
                var repleseLetter = item.weather[0].description.take(1)
                var cloudCover = item.weather[0].description.replaceFirst(repleseLetter, firstLetter, true)
                textCloudCover.text = cloudCover
                textSpeedWind.text = item.wind.speed.toString() + " м/с"
                when (item.wind.deg) {
                    in 0..22 -> {
                        textDirectionWind.text = "Северный"
                    }

                    in 23..67 -> {
                        textDirectionWind.text = "Северно-Восточный"
                    }

                    in 67..112 -> {
                        textDirectionWind.text = "Восточный"
                    }

                    in 112..158 -> {
                        textDirectionWind.text = "Юго-Восточный"
                    }

                    in 158..202 -> {
                        textDirectionWind.text = "Южный"
                    }

                    in 202..248 -> {
                        textDirectionWind.text = "Юго-Западный"
                    }

                    in 248..292 -> {
                        textDirectionWind.text = "Восточный"
                    }

                    in 292..338 -> {
                        textDirectionWind.text = "Северо-Восточный"
                    }

                    in 338..360 -> {
                        textDirectionWind.text = "Северный"
                    }
                }
                textMaxWind.text = item.wind.gust.toString() + " м/с"
                textProbabilityOfPrecipitation.text = (item.pop * 100).toString() + " %"
                textPercentageOfClouds.text = item.clouds.all.toString() + " %"
                textVisibility.text = item.visibility.toString() + " м"
            }

        }
    }

}

class FiveDayViewHolder(val binding: FiveDayItemBinding) :
    RecyclerView.ViewHolder(binding.root)
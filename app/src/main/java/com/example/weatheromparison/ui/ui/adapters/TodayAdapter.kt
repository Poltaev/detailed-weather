package com.example.weatheromparison.ui.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatheromparison.databinding.TodayForItemBinding
import com.example.weatheromparison.ui.entity.Temp

class TodayAdapter (
    private val data: List<Temp>,
) :
    RecyclerView.Adapter<TodayViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodayViewHolder {
        val binding = TodayForItemBinding.inflate(LayoutInflater.from(parent.context))
        return TodayViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {

        val item = data.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                textDataAndTime.text = item.dt_txt
                textTempo.text = item.main.temp.toString() + " °С"
                textTempoFell.text = item.main.feels_like.toString() + " °С"
                textTempoMax.text = item.main.temp_max.toString() + " °С"
                textTempoMin.text = item.main.temp_min.toString() + " °С"
                textCloudCover.text = item.weather[0].description
                textSpeedWind.text = item.wind.speed.toString() + " м/с"
                when(item.wind.deg) {
                  in  0.. 22 -> {
                      textDirectionWind.text = "Северный"
                  }
                    in  23.. 67 -> {
                        textDirectionWind.text = "Северно-Восточный"
                    }
                    in  67.. 112 -> {
                        textDirectionWind.text = "Восточный"
                    }
                    in  112.. 158 -> {
                        textDirectionWind.text = "Юго-Восточный"
                    }
                    in  158.. 202 -> {
                        textDirectionWind.text = "Южный"
                    }
                    in  202.. 248 -> {
                        textDirectionWind.text = "Юго-Западный"
                    }
                    in  248.. 292 -> {
                        textDirectionWind.text = "Восточный"
                    }
                    in  292.. 338 -> {
                        textDirectionWind.text = "Северо-Восточный"
                    }
                    in  338.. 360 -> {
                        textDirectionWind.text = "Северный"
                    }
                }
                textMaxWind.text = item.wind.gust.toString()+ " м/с"
                textProbabilityOfPrecipitation.text = item.pop.toString() + " %"
                textPercentageOfClouds.text = item.clouds.all.toString()  + " %"
                textVisibility.text = item.visibility.toString()  + " м"
            }

        }
    }

}

class TodayViewHolder(val binding: TodayForItemBinding) :
    RecyclerView.ViewHolder(binding.root)
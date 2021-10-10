package com.cmt.weather.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.cmt.weather.R
import com.cmt.weather.databinding.ForecastHistoryItemBinding
import java.util.*

class ForeCastAdapter(
    private val context : Context,
    private val mDataList: List<ForeCastResultView>
) :
    RecyclerView.Adapter<ForeCastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = ForecastHistoryItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val result = mDataList[position]
        holder.tvDate.text = String.format(
            Locale.getDefault(),
            context.getString(R.string.current_date_s),
            result.date
        )
        holder.tvWeatherCondition.text = String.format(
            Locale.getDefault(),
            context.getString(R.string.weather_condition_s),
            result.weather
        )
        holder.tvMaxTemp.text = String.format(
            Locale.getDefault(),
            context.getString(R.string.max_temperature),
            result.maxTemp.toString()
        )
        holder.tvMinTemp.text = String.format(
            Locale.getDefault(),
            context.getString(R.string.min_temperature),
            result.minTemp.toString()
        )
        holder.tvDayTemp.text = String.format(
            Locale.getDefault(),
            context.getString(R.string.day_temperature),
            result.maxTemp.toString()
        )
        holder.tvMornTemp.text = String.format(
            Locale.getDefault(),
            context.getString(R.string.morn_temperature),
            result.minTemp.toString()
        )
        holder.tvEveTemp.text = String.format(
            Locale.getDefault(),
            context.getString(R.string.eve_temperature),
            result.maxTemp.toString()
        )
        holder.tvNightTemp.text = String.format(
            Locale.getDefault(),
            context.getString(R.string.night_temperature),
            result.minTemp.toString()
        )
        holder.tvWindPressure.text = String.format(
            Locale.getDefault(),
            context.getString(R.string.wind_pressure),
            result.windPressure.toString()
        )
        holder.tvHumidity.text = String.format(
            Locale.getDefault(),
            context.getString(R.string.humidity),
            result.humidity.toString()
        )
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvDate: AppCompatTextView = itemView.findViewById(R.id.tvDate)
        val tvWeatherCondition: AppCompatTextView = itemView.findViewById(R.id.tvWeatherCondition)
        val tvMaxTemp: AppCompatTextView = itemView.findViewById(R.id.tvMaxTemp)
        val tvMinTemp: AppCompatTextView = itemView.findViewById(R.id.tvMinTemp)
        val tvDayTemp: AppCompatTextView = itemView.findViewById(R.id.tvDayTemp)
        val tvMornTemp: AppCompatTextView = itemView.findViewById(R.id.tvMornTemp)
        val tvEveTemp: AppCompatTextView = itemView.findViewById(R.id.tvEveTemp)
        val tvNightTemp: AppCompatTextView = itemView.findViewById(R.id.tvNightTemp)
        val tvWindPressure: AppCompatTextView = itemView.findViewById(R.id.tvWindPressure)
        val tvHumidity: AppCompatTextView = itemView.findViewById(R.id.tvHumidity)
    }
}
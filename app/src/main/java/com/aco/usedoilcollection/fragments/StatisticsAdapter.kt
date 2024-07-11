package com.aco.usedoilcollection.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aco.usedoilcollection.R
import com.aco.usedoilcollection.database.entities.OilCollectionRecord

class StatisticsAdapter : ListAdapter<Triple<OilCollectionRecord, String, String>, StatisticsAdapter.StatisticsViewHolder>(StatisticsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_statistics, parent, false)
        return StatisticsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        val (record, userName, locationName) = getItem(position)
        holder.bind(record, userName, locationName)
    }

    class StatisticsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recordIdTextView: TextView = itemView.findViewById(R.id.record_id)
        private val recordDateTimeTextView: TextView = itemView.findViewById(R.id.record_date_time)
        private val recordUserTextView: TextView = itemView.findViewById(R.id.record_user)
        private val recordLocationTextView: TextView = itemView.findViewById(R.id.record_location)
        private val recordLitersTextView: TextView = itemView.findViewById(R.id.record_liters)

        fun bind(record: OilCollectionRecord, userName: String, locationName: String) {
            recordIdTextView.text = "ID: ${record.id}"
            recordDateTimeTextView.text = formatDateTime(record.dateTime)
            recordUserTextView.text = userName
            recordLocationTextView.text = record.locationName
            recordLitersTextView.text = "${record.litersCollected} Ltrs"
        }

        private fun formatDateTime(millis: Long): String {
            val sdf = java.text.SimpleDateFormat("dd/MM/yy 'at' HH:mm", java.util.Locale.getDefault())
            return sdf.format(java.util.Date(millis))
        }
    }

    class StatisticsDiffCallback : DiffUtil.ItemCallback<Triple<OilCollectionRecord, String, String>>() {
        override fun areItemsTheSame(oldItem: Triple<OilCollectionRecord, String, String>, newItem: Triple<OilCollectionRecord, String, String>): Boolean {
            return oldItem.first.id == newItem.first.id
        }

        override fun areContentsTheSame(oldItem: Triple<OilCollectionRecord, String, String>, newItem: Triple<OilCollectionRecord, String, String>): Boolean {
            return oldItem == newItem
        }
    }
}

package com.aco.usedoilcollection.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aco.usedoilcollection.R
import com.aco.usedoilcollection.database.entities.Location
import com.aco.usedoilcollection.viewmodel.LocationViewModel

class LocationsAdapter(
    private val viewModel: LocationViewModel
) : ListAdapter<Location, LocationsAdapter.LocationViewHolder>(LocationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return LocationViewHolder(view, viewModel)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        holder.bind(location)
    }

    class LocationViewHolder(itemView: View, private val viewModel: LocationViewModel) : RecyclerView.ViewHolder(itemView) {
        private val locationNameEditText: EditText = itemView.findViewById(R.id.location_name)
        private val editButton: ImageButton = itemView.findViewById(R.id.edit_button)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)

        fun bind(location: Location) {
            locationNameEditText.setText(location.name)
            editButton.setOnClickListener {
                val newName = locationNameEditText.text.toString()
                if (newName.isNotBlank()) {
                    viewModel.updateLocation(location.copy(name = newName))
                }
            }
            deleteButton.setOnClickListener {
                viewModel.deleteLocation(location)
            }
        }
    }

    class LocationDiffCallback : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }
    }
}

package com.aco.usedoilcollection.fragments

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
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

    override fun submitList(list: List<Location>?) {
        super.submitList(list?.sortedWith(compareBy {
            it.name.substringAfter("Location ").toIntOrNull() ?: 0
        }))
    }

    class LocationViewHolder(itemView: View, private val viewModel: LocationViewModel) : RecyclerView.ViewHolder(itemView) {
        private val locationNameTextView: TextView = itemView.findViewById(R.id.location_name)
        private val locationNameEditText: EditText = itemView.findViewById(R.id.location_name_edit)
        private val menuButton: ImageButton = itemView.findViewById(R.id.menu_button)
        private val saveButton: ImageButton = itemView.findViewById(R.id.save_button)

        fun bind(location: Location) {
            locationNameTextView.text = location.name
            locationNameEditText.setText(location.name)

            menuButton.setOnClickListener {
                showPopupMenu(it, location)
            }

            saveButton.setOnClickListener {
                val newName = locationNameEditText.text.toString()
                if (newName.isNotBlank()) {
                    viewModel.updateLocation(location.copy(name = newName))
                    locationNameTextView.text = newName
                    switchToViewMode()
                }
            }

            switchToViewMode()
        }

        private fun switchToEditMode() {
            locationNameTextView.visibility = View.GONE
            locationNameEditText.visibility = View.VISIBLE
            saveButton.visibility = View.VISIBLE
            menuButton.visibility = View.GONE
        }

        private fun switchToViewMode() {
            locationNameTextView.visibility = View.VISIBLE
            locationNameEditText.visibility = View.GONE
            saveButton.visibility = View.GONE
            menuButton.visibility = View.VISIBLE
        }

        private fun showPopupMenu(view: View, location: Location) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menuInflater.inflate(R.menu.menu_location, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_edit -> {
                        switchToEditMode()
                        true
                    }
                    R.id.action_delete -> {
                        showDeleteConfirmationDialog(view, location)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

        private fun showDeleteConfirmationDialog(view: View, location: Location) {
            AlertDialog.Builder(view.context)
                .setTitle("Delete Location")
                .setMessage("Are you sure you want to delete this location?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteLocation(location)
                }
                .setNegativeButton("No", null)
                .show()
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

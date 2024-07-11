package com.aco.usedoilcollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aco.usedoilcollection.MainActivity
import com.aco.usedoilcollection.R
import com.aco.usedoilcollection.utils.getCurrentDate
import com.aco.usedoilcollection.viewmodel.LocationViewModel
import com.aco.usedoilcollection.viewmodel.LocationViewModelFactory
import com.aco.usedoilcollection.viewmodel.OilCollectionViewModel
import kotlinx.coroutines.launch

class InputFragment : Fragment() {

    private lateinit var locationViewModel: LocationViewModel
    private lateinit var oilCollectionViewModel: OilCollectionViewModel
    private var remainingVolume: Int = 1800 // Установите здесь начальное значение
    private lateinit var currentDateTextView: TextView
    private lateinit var remainingVolumeTextView: TextView
    private lateinit var litersInput: EditText
    private lateinit var locationSelector: TextView
    private lateinit var addButton: Button
    private var selectedLocationId: Int = -1
    private var selectedLocation: String = "Select location"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)
        currentDateTextView = view.findViewById(R.id.current_date)
        remainingVolumeTextView = view.findViewById(R.id.remaining_volume)
        litersInput = view.findViewById(R.id.liters_input)
        locationSelector = view.findViewById(R.id.location_selector)
        addButton = view.findViewById(R.id.add_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = (requireActivity() as MainActivity).locationRepository
        locationViewModel = ViewModelProvider(this, LocationViewModelFactory(repository)).get(LocationViewModel::class.java)
        oilCollectionViewModel = ViewModelProvider(requireActivity()).get(OilCollectionViewModel::class.java)

        currentDateTextView.text = getCurrentDate()
        viewLifecycleOwner.lifecycleScope.launch {
            oilCollectionViewModel.remainingVolume.collect { volume ->
                remainingVolume = volume
                remainingVolumeTextView.text = "Remaining Volume: ${remainingVolume}L"
            }
        }

        addButton.setOnClickListener {
            handleAddLiters()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            locationViewModel.locations.collect { locations ->
                val popupMenu = PopupMenu(requireContext(), locationSelector)
                locations.forEach { location ->
                    popupMenu.menu.add(Menu.NONE, location.id, Menu.NONE, location.name)
                }
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    selectedLocation = menuItem.title.toString()
                    selectedLocationId = menuItem.itemId
                    locationSelector.text = selectedLocation
                    true
                }
                locationSelector.setOnClickListener {
                    popupMenu.show()
                }
            }
        }
    }

    private fun handleAddLiters() {
        val litersText = litersInput.text.toString()
        val enteredLiters = litersText.toIntOrNull() ?: 0
        if (enteredLiters == 0) {
            Toast.makeText(requireContext(), "Please enter a value greater than zero.", Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedLocation == "Select location") {
            Toast.makeText(requireContext(), "Please enter a location.", Toast.LENGTH_SHORT).show()
            return
        }
        if (remainingVolume == 0) {
            Toast.makeText(requireContext(), "You have reached the limit, there is no more available space.", Toast.LENGTH_SHORT).show()
            litersInput.setText("")
        } else if (enteredLiters in 1..remainingVolume) {
            showDialog(enteredLiters)
        } else {
            Toast.makeText(requireContext(), "You can only have $remainingVolume liters or less.", Toast.LENGTH_SHORT).show()
            litersInput.setText("")
        }
    }

    private fun showDialog(enteredLiters: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Action")
            .setMessage("Are you sure you want to add $enteredLiters liters to $selectedLocation?")
            .setPositiveButton("Yes") { _, _ ->
                if (enteredLiters <= remainingVolume) {
                    val currentDateTime = System.currentTimeMillis()
                    oilCollectionViewModel.addRecord(currentDateTime, enteredLiters, selectedLocationId)
                    Toast.makeText(requireContext(), "Added $enteredLiters liters to $selectedLocation.", Toast.LENGTH_SHORT).show()
                    litersInput.setText("")
                    selectedLocation = "Select location"
                    selectedLocationId = -1
                    locationSelector.text = selectedLocation
                } else {
                    Toast.makeText(requireContext(), "You can only have $remainingVolume liters or less.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}

package com.aco.usedoilcollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aco.usedoilcollection.R
import com.aco.usedoilcollection.viewmodel.LocationViewModel
import com.aco.usedoilcollection.viewmodel.LocationViewModelFactory
import com.aco.usedoilcollection.MainActivity
import kotlinx.coroutines.launch

class LocationsFragment : Fragment() {

    private lateinit var locationViewModel: LocationViewModel
    private lateinit var locationsRecyclerView: RecyclerView
    private lateinit var locationsAdapter: LocationsAdapter
    private lateinit var newLocationNameEditText: EditText
    private lateinit var addLocationButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_locations, container, false)
        newLocationNameEditText = view.findViewById(R.id.new_location_name)
        addLocationButton = view.findViewById(R.id.add_location_button)
        locationsRecyclerView = view.findViewById(R.id.locations_recycler_view)
        locationsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = (requireActivity() as MainActivity).locationRepository
        locationViewModel = ViewModelProvider(this, LocationViewModelFactory(repository)).get(LocationViewModel::class.java)

        locationsAdapter = LocationsAdapter(locationViewModel)
        locationsRecyclerView.adapter = locationsAdapter

        addLocationButton.setOnClickListener {
            val name = newLocationNameEditText.text.toString()
            if (name.isNotBlank()) {
                locationViewModel.addLocation(name)
                newLocationNameEditText.text.clear()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            locationViewModel.locations.collect { locations ->
                locationsAdapter.submitList(locations)
            }
        }
    }
}

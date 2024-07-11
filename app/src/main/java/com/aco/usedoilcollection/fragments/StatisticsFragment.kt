package com.aco.usedoilcollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aco.usedoilcollection.R
import com.aco.usedoilcollection.viewmodel.OilCollectionViewModel
import com.aco.usedoilcollection.viewmodel.OilCollectionViewModelFactory
import com.aco.usedoilcollection.MainActivity
import kotlinx.coroutines.launch

class StatisticsFragment : Fragment() {

    private lateinit var oilCollectionViewModel: OilCollectionViewModel
    private lateinit var statisticsRecyclerView: RecyclerView
    private lateinit var statisticsAdapter: StatisticsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        statisticsRecyclerView = view.findViewById(R.id.statistics_recycler_view)
        statisticsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = (requireActivity() as MainActivity).oilCollectionRepository
        oilCollectionViewModel = ViewModelProvider(this, OilCollectionViewModelFactory(repository)).get(OilCollectionViewModel::class.java)

        statisticsAdapter = StatisticsAdapter()
        statisticsRecyclerView.adapter = statisticsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            oilCollectionViewModel.collectionHistory.collect { collectionHistory ->
                statisticsAdapter.submitList(collectionHistory)
            }
        }
    }
}

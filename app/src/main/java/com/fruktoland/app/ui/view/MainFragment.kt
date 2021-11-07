package com.fruktoland.app.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.fruktoland.app.R
import com.fruktoland.app.common.*
import com.fruktoland.app.databinding.FragmentMainBinding
import com.fruktoland.app.extensions.navigate
import com.fruktoland.app.ui.state.CatalogDataUpdate
import com.fruktoland.app.ui.state.Default
import com.fruktoland.app.ui.viewModel.MainFragmentViewModel
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.fruitsItem.setOnClickListener { view -> onHomeItemClicked(view) }
        binding.vegetablesItem.setOnClickListener { view -> onHomeItemClicked(view) }
        binding.berriesItem.setOnClickListener { view -> onHomeItemClicked(view) }
        binding.saladsItem.setOnClickListener { view -> onHomeItemClicked(view) }
        binding.meatItem.setOnClickListener { view -> onHomeItemClicked(view) }
        binding.nutsItem.setOnClickListener { view -> onHomeItemClicked(view) }
        binding.othersItem.setOnClickListener { view -> onHomeItemClicked(view) }

        lifecycleScope.launchWhenCreated {
//            initView()
//            viewModel.checkServiceState()
        }

        lifecycleScope.launchWhenStarted {
            initObservers()
        }

        return binding.root
    }

    suspend fun initObservers() {
        viewModel.state.collect { viewState ->
            when (viewState) {
                is Default -> {

                }
                is Error -> {
                    showToastMessage(viewState.description)
                }
                is CatalogDataUpdate -> {

                }
                else -> {

                }
            }
        }
    }

    private fun onHomeItemClicked(view: View) {
        navigate(
            MainFragmentDirections.actionMainFragmentToCatalogFragment(getCatalogName(view.id))
        )
    }

    private fun getCatalogName(viewId: Int): CatalogNames =
        when (viewId) {
            R.id.berriesItem -> Barries()
            R.id.fruitsItem -> Fruits()
            R.id.vegetablesItem -> Vegetables()
            R.id.saladsItem -> Salads()
            R.id.meatItem -> Meats()
            R.id.nutsItem -> Nuts()
            R.id.othersItem -> Other()
            else -> Unknown()
        }

    private fun showToastMessage(description: String) {
        requireContext().also { context ->
            Toast.makeText(context, description, Toast.LENGTH_LONG).show()
        }
    }
}
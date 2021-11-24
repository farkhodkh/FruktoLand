package com.fruktoland.app.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.fruktoland.app.R
import com.fruktoland.app.common.*
import com.fruktoland.app.databinding.FragmentMainBinding
import com.fruktoland.app.extensions.navigate
import com.fruktoland.app.ui.adapter.CatalogAdapter
import com.fruktoland.app.ui.state.MainDataUpdate
import com.fruktoland.app.ui.state.MainDefault
import com.fruktoland.app.ui.state.MainEmpty
import com.fruktoland.app.ui.state.MainError
import com.fruktoland.app.ui.viewModel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainFragmentViewModel by activityViewModels()

    @Inject
    lateinit var catalogAdapter: CatalogAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.catalogRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.catalogRecyclerView.adapter = catalogAdapter

        catalogAdapter.onClickAction = this::onHomeItemClicked
        lifecycleScope.launchWhenCreated {
            viewModel.getCatalogs()
        }

        lifecycleScope.launchWhenStarted {
            initObservers()
        }

        return binding.root
    }

    suspend fun initObservers() {
        viewModel.state.collect { viewState ->
            when (viewState) {
                is MainDefault -> {

                }
                is MainEmpty -> {

                }
                is MainDataUpdate -> {
                    catalogAdapter.catalogList = viewState.catalogs
                    catalogAdapter.notifyDataSetChanged()
                }
                is MainError -> {
                    showToastMessage(viewState.description)
                }
            }
        }
    }

    private fun onHomeItemClicked(catalogName: String, catalogId: Long) {
        navigate(
            MainFragmentDirections.actionMainFragmentToCatalogFragment(catalogName, catalogId)
        )
    }

//    private fun getCatalogName(viewId: Int): CatalogNames =
//        when (viewId) {
//            R.id.berriesItem -> Barries()
//            R.id.catalogItem -> Fruits()
//            R.id.vegetablesItem -> Vegetables()
//            R.id.saladsItem -> Salads()
//            R.id.meatItem -> Meats()
//            R.id.nutsItem -> Nuts()
//            R.id.othersItem -> Other()
//            else -> Unknown()
//        }

    private fun showToastMessage(description: String) {
        requireContext().also { context ->
            Toast.makeText(context, description, Toast.LENGTH_LONG).show()
        }
    }
}
package com.fruktoland.app.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.fruktoland.app.databinding.CatalogFragmentBinding
import com.fruktoland.app.ui.adapter.CatalogItemsAdapter
import com.fruktoland.app.ui.state.CatalogDataUpdate
import com.fruktoland.app.ui.state.CatalogDefault
import com.fruktoland.app.ui.state.CatalogEmpty
import com.fruktoland.app.ui.state.CatalogError
import com.fruktoland.app.ui.viewModel.CatalogFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class CatalogFragment : Fragment() {
    private lateinit var binding: CatalogFragmentBinding
    private val viewModel: CatalogFragmentViewModel by activityViewModels()
    private val catalogName: String by lazy { CatalogFragmentArgs.fromBundle(requireArguments()).catalogName }
    private val catalogId: Long by lazy {CatalogFragmentArgs.fromBundle(requireArguments()).catalogId }

    @Inject
    lateinit var itemsAdapter: CatalogItemsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CatalogFragmentBinding.inflate(inflater, container, false)

        binding.txtViewCatalogName.text = catalogName

        binding.catalogRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.catalogRecyclerView.adapter = itemsAdapter

        itemsAdapter.notifyDataSetChanged()

        lifecycleScope.launchWhenResumed {
            setStateDownloading(true)
            viewModel
                .getCatalogItems(catalogId.toString())
        }

        lifecycleScope.launchWhenStarted {
            initObservers()
        }

        return binding.root
    }

    private fun setStateDownloading(downloading: Boolean) {
        binding.imgDownloading.isVisible = downloading
        binding.progressBarDownloading.isVisible = downloading
    }

    suspend fun initObservers() {
        viewModel.state.collect { viewState ->
            when (viewState) {
                is CatalogDefault -> {

                }
                is CatalogError -> {
                    showToastMessage(viewState.description)
                    itemsAdapter.catalogItemsList = emptyList()
                    itemsAdapter.notifyDataSetChanged()
                }
                is CatalogEmpty -> {
                    showToastMessage(viewState.description)
                    itemsAdapter.catalogItemsList = viewState.itemsList
                    itemsAdapter.notifyDataSetChanged()
                }
                is CatalogDataUpdate -> {
                    itemsAdapter.catalogItemsList = viewState.itemsList
                    itemsAdapter.notifyDataSetChanged()
                }
            }
            setStateDownloading(false)
        }
    }

    private fun showToastMessage(description: String) {
        requireContext().also { context ->
            Toast.makeText(context, description, Toast.LENGTH_LONG).show()
        }
    }
}
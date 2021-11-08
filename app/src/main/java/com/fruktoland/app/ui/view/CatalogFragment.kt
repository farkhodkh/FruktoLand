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
import com.fruktoland.app.common.CatalogNames
import com.fruktoland.app.databinding.CatalogFragmentBinding
import com.fruktoland.app.ui.adapter.CatalogAdapter
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
    private val catalogName: CatalogNames? by lazy { CatalogFragmentArgs.fromBundle(requireArguments()).catalogName }

    @Inject
    lateinit var adapter: CatalogAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CatalogFragmentBinding.inflate(inflater, container, false)

        binding.txtViewCatalogName.text = catalogName?.nameRu

        binding.catalogRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.catalogRecyclerView.adapter = adapter

        adapter.notifyDataSetChanged()

        lifecycleScope.launchWhenResumed {
            viewModel.addCatalogItems(catalogName)
            setStateDownloading(true)
            viewModel
                .getCatalogItems(catalogName)
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
                    adapter.catalogList = emptyList()
                    adapter.notifyDataSetChanged()
                }
                is CatalogEmpty -> {
                    showToastMessage(viewState.description)
                    adapter.catalogList = viewState.itemsList
                    adapter.notifyDataSetChanged()
                }
                is CatalogDataUpdate -> {
                    adapter.catalogList = viewState.itemsList
                    adapter.notifyDataSetChanged()
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
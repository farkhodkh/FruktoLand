package com.fruktoland.app.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.fruktoland.app.common.CatalogNames
import com.fruktoland.app.common.Const
import com.fruktoland.app.databinding.CatalogFragmentBinding
import com.fruktoland.app.extensions.navigate
import com.fruktoland.app.ui.adapter.CatalogAdapter
import com.fruktoland.app.ui.viewModel.CatalogFragmentViewModel

class CatalogFragment : Fragment() {
    private lateinit var binding: CatalogFragmentBinding
    private val viewModel: CatalogFragmentViewModel by activityViewModels()
    private val catalogName: CatalogNames? by lazy { CatalogFragmentArgs.fromBundle(requireArguments()).catalogName }

    private lateinit var adapter: CatalogAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CatalogFragmentBinding.inflate(inflater, container, false)
        binding.btnBack.setOnClickListener {
            navigate(CatalogFragmentDirections.actionCatalogFragmentToMainFragment())
        }
        binding.txtViewCatalogName.text = catalogName?.nameRu

        adapter = CatalogAdapter(Const.getEmptyList())
        binding.catalogRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.catalogRecyclerView.adapter = adapter

        adapter.notifyDataSetChanged()

        lifecycleScope.launchWhenResumed {
            viewModel.getCatalogItems(catalogName)
        }

        return binding.root
    }
}
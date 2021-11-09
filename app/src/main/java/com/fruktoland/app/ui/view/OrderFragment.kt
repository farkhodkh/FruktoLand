package com.fruktoland.app.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fruktoland.app.databinding.FragmentOrderBinding
import com.fruktoland.app.extensions.navigate
import com.fruktoland.app.ui.adapter.OrderAdapter
import com.fruktoland.app.ui.state.OrderDataUpdate
import com.fruktoland.app.ui.state.OrderEmpty
import com.fruktoland.app.ui.state.OrderError
import com.fruktoland.app.ui.viewModel.OrderFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class OrderFragment : Fragment() {
    private lateinit var binding: FragmentOrderBinding
    private val viewModel: OrderFragmentViewModel by activityViewModels()

    @Inject
    lateinit var adapter: OrderAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        binding.orderListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.orderListRecyclerView.adapter = adapter

        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun getSwipeDirs(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    return ItemTouchHelper.LEFT
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    adapter.removePosition(viewHolder.adapterPosition)
                }
            }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.orderListRecyclerView)

        lifecycleScope.launchWhenResumed {
            viewModel
                .getAllBasket()
        }

        lifecycleScope.launchWhenStarted {
            initObservers()
        }

        binding.btnConfirm.setOnClickListener {
            navigate(
                OrderFragmentDirections.actionOrderFragmentToConfirmFragment()
            )
        }
    }

    suspend fun initObservers() {

        adapter
            .totalSum
            .onEach { total ->
                binding.txtVTotalSumm.text = total
            }
            .launchIn(lifecycleScope)

        viewModel.state.collect { viewState ->
            when (viewState) {
                is OrderEmpty -> {
                    showToastMessage(viewState.description)
                }
                is OrderError -> {
                    showToastMessage(viewState.description)
                }
                is OrderDataUpdate -> {
                    adapter.differ.submitList(viewState.itemsList.toMutableList())
                    //binding.orderListRecyclerView.smoothScrollToPosition(adapter.itemCount)
                }
                else -> {}
            }
        }
    }

    private fun showToastMessage(description: String) {
        requireContext().also { context ->
            Toast.makeText(context, description, Toast.LENGTH_LONG).show()
        }
    }
}
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.fruktoland.app.databinding.FragmentOrderBinding
import com.fruktoland.app.ui.adapter.OrderAdapter
import com.fruktoland.app.ui.state.OrderDataUpdate
import com.fruktoland.app.ui.state.OrderEmpty
import com.fruktoland.app.ui.state.OrderError
import com.fruktoland.app.ui.viewModel.OrderFragmentViewModel
import com.redmadrobot.inputmask.MaskedTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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

        binding.tilPhone.editText?.let {
            MaskedTextChangedListener.installOn(
                editText = it,
                primaryFormat = PRIMARY_FORMAT,
                valueListener = object : MaskedTextChangedListener.ValueListener {
                    override fun onTextChanged(
                        maskFilled: Boolean,
                        extractedValue: String,
                        formattedValue: String
                    ) {
                        binding.btnConfirm.isEnabled = maskFilled
                    }
                })
        }


        binding.orderListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.orderListRecyclerView.adapter = adapter

        lifecycleScope.launchWhenResumed {
            viewModel
                .getAllBasket()
        }

        lifecycleScope.launchWhenStarted {
            initObservers()
        }

        return binding.root
    }

    suspend fun initObservers() {
        viewModel.state.collect { viewState ->
            when(viewState) {
                is OrderEmpty -> {
                    showToastMessage(viewState.description)
                }
                is OrderError -> {
                    showToastMessage(viewState.description)
                }
                is OrderDataUpdate -> {
                    adapter.orderList = viewState.itemsList
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun showToastMessage(description: String) {
        requireContext().also { context ->
            Toast.makeText(context, description, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val PRIMARY_FORMAT = "+7 [000] [000]-[00]-[00]"
    }
}
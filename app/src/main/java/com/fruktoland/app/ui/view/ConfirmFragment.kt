package com.fruktoland.app.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.fruktoland.app.R
import com.fruktoland.app.databinding.FragmentConfirmBinding
import com.fruktoland.app.extensions.navigate
import com.fruktoland.app.ui.state.ConfirmDefault
import com.fruktoland.app.ui.state.ConfirmError
import com.fruktoland.app.ui.state.ConfirmOrderSendStates
import com.fruktoland.app.ui.state.ConfirmUploading
import com.fruktoland.app.ui.viewModel.ConfirmFragmentViewModel
import com.redmadrobot.inputmask.MaskedTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ConfirmFragment : Fragment() {
    private lateinit var binding: FragmentConfirmBinding
    private val viewModel: ConfirmFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmBinding.inflate(inflater, container, false)
        binding.btnOrder.setOnClickListener {
            orderOnClick()
        }

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
                        binding.btnOrder.isEnabled = maskFilled
                    }
                })
        }

        binding.tilFIO.editText?.addTextChangedListener { editable ->
            if (editable?.length == 0) {
                binding.tilFIO.isErrorEnabled = true
                binding.tilFIO.error = "Не указан ФИО получателя"
            } else {
                binding.tilFIO.isErrorEnabled = false
                binding.tilFIO.error = ""
            }
        }

        binding.tilAddress.editText?.addTextChangedListener { editable ->
            if (editable?.length == 0) {
                binding.tilAddress.isErrorEnabled = true
                binding.tilAddress.error = "Не указан  адрес доставки"
            } else {
                binding.tilAddress.isErrorEnabled = false
                binding.tilAddress.error = ""
            }
        }

        lifecycleScope.launchWhenStarted {
            initObservers()
        }

        return binding.root
    }

    fun orderOnClick() {
        viewModel.confirmOrder(
            binding.tilFIO.editText?.text.toString(),
            binding.tilAddress.editText?.text.toString(),
            binding.tilPhone.editText?.text.toString(),
            binding.tilComments.editText?.text.toString()
        )
    }

    suspend fun initObservers() {
        viewModel.state.collect { viewState ->
            when (viewState) {
                is ConfirmDefault -> {
                    setStateDownloading(false)
                }
                is ConfirmUploading -> {
                    setStateDownloading(true)
                }
                is ConfirmError -> {
                    setStateDownloading(false)
                    showToastMessage(viewState.description)
                }
                is ConfirmOrderSendStates -> {
                    setStateDownloading(false)
                    if (viewState.statue) {
                        showToastMessage(resources.getString(R.string.order_send_success))
                        navigate(
                            ConfirmFragmentDirections.actionConfirmFragmentToMainFragment()
                        )
                    } else {
                        showToastMessage(resources.getString(R.string.order_send_error))
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.setDefaultState()
    }
    private fun setStateDownloading(downloading: Boolean) {
        binding.imgDownloading.isVisible = downloading
        binding.progressBarDownloading.isVisible = downloading
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
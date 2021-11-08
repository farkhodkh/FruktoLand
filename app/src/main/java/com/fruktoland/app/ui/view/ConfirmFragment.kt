package com.fruktoland.app.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.fruktoland.app.databinding.FragmentConfirmBinding
import com.fruktoland.app.ui.viewModel.ConfirmFragmentViewModel
import com.redmadrobot.inputmask.MaskedTextChangedListener
import dagger.hilt.android.AndroidEntryPoint

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
        return binding.root
    }


    fun orderOnClick() {

    }

    companion object {
        const val PRIMARY_FORMAT = "+7 [000] [000]-[00]-[00]"
    }
}
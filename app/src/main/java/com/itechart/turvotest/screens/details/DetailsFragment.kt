package com.itechart.turvotest.screens.details

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.itechart.turvotest.R
import com.itechart.turvotest.databinding.FragmentDetailsBinding
import com.itechart.turvotest.screens.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : BaseFragment<DetailsViewModel, FragmentDetailsBinding>() {

    override val layout: Int = R.layout.fragment_details
    override val viewModel: DetailsViewModel by viewModel {
        val args: DetailsFragmentArgs by navArgs()
        parametersOf(args.ticker)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun bindViewModel(binding: FragmentDetailsBinding) {
        binding.viewModel = viewModel
    }

    override fun subscribeToViewModel(viewModel: DetailsViewModel) {
        observe(viewModel.events) {
            when (it) {
                is DetailsViewModelActions.Close -> findNavController().navigateUp()
            }
        }
    }

}
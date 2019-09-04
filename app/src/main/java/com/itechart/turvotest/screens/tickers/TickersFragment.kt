package com.itechart.turvotest.screens.tickers

import androidx.navigation.fragment.findNavController
import com.itechart.turvotest.R
import com.itechart.turvotest.databinding.FragmentTickersBinding
import com.itechart.turvotest.screens.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TickersFragment : BaseFragment<TickersViewModel, FragmentTickersBinding>() {

    override val layout: Int = R.layout.fragment_tickers
    override val viewModel: TickersViewModel by viewModel()

    override fun bindViewModel(binding: FragmentTickersBinding) {
        binding.viewModel = viewModel
    }

    override fun subscribeToViewModel(viewModel: TickersViewModel) {
        observe(viewModel.events) {
            when (it) {
                is TickerViewModelActions.NextClicked -> {
                    findNavController().navigate(
                        TickersFragmentDirections.actionPortfolioFragment(it.tickers.toTypedArray())
                    )
                }
            }
        }
    }

}
package com.itechart.turvotest.screens.portfolio

import com.itechart.turvotest.R
import com.itechart.turvotest.databinding.FragmentPortfolioBinding
import com.itechart.turvotest.screens.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PortfolioFragment: BaseFragment<PortfolioViewModel, FragmentPortfolioBinding>() {

    override val layout: Int = R.layout.fragment_portfolio
    override val viewModel: PortfolioViewModel by viewModel()

    override fun bindViewModel(binding: FragmentPortfolioBinding) {
        binding.viewModel = viewModel
    }

    override fun subscribeToViewModel(viewModel: PortfolioViewModel) {
        observe(viewModel.events) {
            // TODO
        }
    }


}
package com.itechart.turvotest.screens.portfolio

import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.itechart.turvotest.R
import com.itechart.turvotest.databinding.FragmentPortfolioBinding
import com.itechart.turvotest.screens.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PortfolioFragment : BaseFragment<PortfolioViewModel, FragmentPortfolioBinding>() {

    override val layout: Int = R.layout.fragment_portfolio
    override val viewModel: PortfolioViewModel by viewModel {
        val args: PortfolioFragmentArgs by navArgs()
        parametersOf(args.tickers)
    }

    override fun bindViewModel(binding: FragmentPortfolioBinding) {
        binding.viewModel = viewModel
    }

    override fun subscribeToViewModel(viewModel: PortfolioViewModel) {
        observe(viewModel.events) {
            when (it) {
                is PortfolioViewModelActions.TickerClicked -> {
                    val extras = FragmentNavigatorExtras(
                        it.title to "title",
                        it.price to "price",
                        it.chart to "chart"
                    )
                    findNavController().navigate(
                        PortfolioFragmentDirections.actionPortfolioFragmentToDetailsFragment(it.ticker),
                        extras
                    )
                }
                is PortfolioViewModelActions.Close -> findNavController().navigateUp()
            }
        }
    }


}
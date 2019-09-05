package com.itechart.turvotest.screens.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.itechart.turvotest.common.utils.safetySubscribe
import com.itechart.turvotest.screens.common.viewmodel.BaseActionsViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import timber.log.Timber

abstract class BaseFragment<ViewModel : BaseActionsViewModel<*>, BindingType : ViewDataBinding> :
    Fragment() {

    @get:LayoutRes
    abstract val layout: Int
    abstract val viewModel: ViewModel

    protected lateinit var binding: BindingType
        private set

    private val disposables = CompositeDisposable()

    abstract fun bindViewModel(binding: BindingType)

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil
            .inflate<BindingType>(inflater, layout, container, false)
            .let {
                binding = it
                binding.lifecycleOwner = viewLifecycleOwner
                it.root
            }
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindViewModel(binding)
    }

    protected open fun initView() = Unit
    protected open fun subscribeToViewModel(viewModel: ViewModel) = Unit

    override fun onStart() {
        super.onStart()
        subscribeToViewModel(viewModel)
        viewModel.start()
    }

    override fun onResume() {
        super.onResume()
        viewModel.resume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pause()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stop()
        disposables.clear()
    }


    protected fun <T> observe(what: Observable<T>, action: (T) -> Unit) {
        what.safetySubscribe(
            Consumer { result: T -> action(result) },
            Consumer { error -> Timber.e(error) }
        ).connectToLifecycle()
    }

    private fun Disposable.connectToLifecycle() = disposables.add(this)

}
package com.itechart.turvotest.common.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itechart.turvotest.screens.common.list.ListItemActions
import com.itechart.turvotest.screens.common.list.ListItemView
import com.itechart.turvotest.screens.common.list.RecyclerBindingAdapter
import io.reactivex.subjects.PublishSubject

object RecyclerViewBinding {

    @BindingAdapter("initAdapter")
    fun setupAdapter(recycler: RecyclerView, subject: PublishSubject<ListItemActions>) {
        if (recycler.adapter == null) {
            recycler.adapter = RecyclerBindingAdapter(eventsObserver = subject)
        }
    }

    @BindingAdapter("viewModelItems")
    fun <T : ListItemView<*>> setupRecyclerViewViewModelItems(
        recycler: RecyclerView,
        items: List<T>?
    ) {
        (recycler.adapter as? RecyclerBindingAdapter)?.setItems(items ?: emptyList())
    }

}
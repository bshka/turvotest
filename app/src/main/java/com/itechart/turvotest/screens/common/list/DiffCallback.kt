package com.itechart.turvotest.screens.common.list

import androidx.recyclerview.widget.DiffUtil

class DiffCallback : DiffUtil.ItemCallback<ListItemView<*>>() {
    override fun areItemsTheSame(oldItem: ListItemView<*>, newItem: ListItemView<*>): Boolean =
        oldItem.isTheSameItem(newItem)

    override fun areContentsTheSame(oldItem: ListItemView<*>, newItem: ListItemView<*>): Boolean =
        oldItem.hasTheSameContent(newItem)
}

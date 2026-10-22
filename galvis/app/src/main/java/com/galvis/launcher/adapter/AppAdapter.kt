package com.galvis.launcher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.galvis.launcher.R
import com.galvis.launcher.model.AppItem
import com.galvis.launcher.ui.FocusScaleHelper

class AppAdapter(
    @LayoutRes private val layoutResId: Int,
    private val onItemClick: (AppItem) -> Unit,
    private val onItemLongClick: (AppItem) -> Boolean,
    private val onItemFocused: (AppItem, isFocused: Boolean) -> Unit
) : ListAdapter<AppItem, AppAdapter.AppViewHolder>(AppDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
        private val tvLabel: TextView? = itemView.findViewById(R.id.tvLabel)
        private val cardContainer: View = itemView.findViewById(R.id.cardContainer) ?: itemView

        init {
            cardContainer.clipToOutline = true

            FocusScaleHelper.attachFocusAnimation(cardContainer) { isFocused ->
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onItemFocused(getItem(pos), isFocused)
                }
                // Smart Name Reveal: reveal label under tile only when focused
                tvLabel?.visibility = if (isFocused) View.VISIBLE else View.INVISIBLE
            }

            cardContainer.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(pos))
                }
            }

            cardContainer.setOnLongClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onItemLongClick(getItem(pos))
                } else {
                    false
                }
            }
        }

        fun bind(item: AppItem) {
            tvLabel?.text = item.label
            ivIcon.setImageDrawable(item.icon)
        }
    }

    private class AppDiffCallback : DiffUtil.ItemCallback<AppItem>() {
        override fun areItemsTheSame(oldItem: AppItem, newItem: AppItem): Boolean {
            return oldItem.packageName == newItem.packageName && oldItem.inputId == newItem.inputId
        }

        override fun areContentsTheSame(oldItem: AppItem, newItem: AppItem): Boolean {
            return oldItem == newItem
        }
    }
}

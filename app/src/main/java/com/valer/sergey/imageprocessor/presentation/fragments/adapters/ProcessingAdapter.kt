package com.valer.sergey.imageprocessor.presentation.fragments.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.valer.sergey.imageprocessor.R
import kotlinx.android.synthetic.main.processed_item.view.*

class ProcessingAdapter(
        items: MutableList<Bitmap> = mutableListOf(),
        val useBitmapAsCurrent: (Bitmap) -> Unit,
        val removeitem: (Int) -> Unit
) : RecyclerView.Adapter<ProcessingAdapter.ProcessingViewHolder>() {

    var items: MutableList<Bitmap> = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addItem(bitmap: Bitmap) {
        items.add(bitmap)
        notifyItemInserted(items.lastIndex)
    }

    fun useAsCurrent(position: Int) {
        useBitmapAsCurrent.invoke(items[position])
    }

    fun deleteItem(position: Int) {
        items.removeAt(position)
        removeitem.invoke(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcessingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.processed_item, parent, false)
        return ProcessingViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: ProcessingViewHolder, position: Int) {
        holder.setImage(items[position])
        holder.setBackground(position)
    }

    inner class ProcessingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.processed_image
        val itemContainer = view.processed_item_container

        init {
            itemContainer?.let { container ->
                container.setOnClickListener {
                    val popupMenu = PopupMenu(this.itemView.context, container).apply {
                        inflate(R.menu.popup_processed_menu)
                        setOnMenuItemClickListener {
                            when (it.itemId) {
                                R.id.popup_use -> {
                                    useAsCurrent(adapterPosition)
                                    true
                                }
                                R.id.popup_delete -> {
                                    deleteItem(adapterPosition)
                                    true
                                }
                                else -> false
                            }
                        }
                    }
                    popupMenu.show()
                }
            }
        }

        private fun getColor (colorRes: Int) = ContextCompat.getColor(this.itemView.context, colorRes)

        fun setBackground(position: Int) {
            itemContainer.setBackgroundColor(if ((position + 1) % 2 == 0) getColor(R.color.colorInactive) else getColor(R.color.colorPrimary))
        }

        fun setImage(bitmap: Bitmap) {
            image?.setImageBitmap(bitmap)
        }
    }
}
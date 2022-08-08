package com.example.poc2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samsung.multiscreen.Service

class TVsAdapter(
    private val context: Context,
    val onClickListener: (Service) -> Unit,
) : RecyclerView.Adapter<TVsAdapter.ViewHolder>() {

    private val tvs: MutableList<Service> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tv = tvs[position]
        val textView = holder.itemView.findViewById<TextView>(R.id.tvName)
        textView.text = tv.name
    }

    override fun getItemCount() = tvs.size

    fun addItem(tv: Service) {
        tvs.add(tv)
        notifyItemChanged(tvs.size - 1)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                onClickListener(tvs[adapterPosition])
            }
        }
    }
}
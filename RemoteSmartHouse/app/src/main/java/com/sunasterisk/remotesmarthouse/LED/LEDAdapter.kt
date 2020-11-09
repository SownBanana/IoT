package com.sunasterisk.remotesmarthouse.LED

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunasterisk.remotesmarthouse.R
import com.sunasterisk.remotesmarthouse.model.LED

class LEDAdapter(
    var list: MutableList<LED>) : RecyclerView.Adapter<LEDAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(list?.get(position))
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item, parent, false))
    }

    fun addLed(led: LED) {
        if (!list.contains(led)) {
            list.add(led)
            notifyItemInserted(list.size)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val title: TextView = itemView.findViewById(R.id.title)
//
        fun bindData(led: LED) {
        }
    }
}

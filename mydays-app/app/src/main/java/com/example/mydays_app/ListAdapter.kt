package com.example.mydays_app

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mydays_app.model.DiaryRecord


class ListAdapter: RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    fun interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }
    private var listener: OnItemClickListener? = null

    fun setListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ListViewHolder(v: View, listener: OnItemClickListener?): RecyclerView.ViewHolder(v){
        val textViewDay: TextView = v.findViewById(R.id.textViewDay)
        val textViewTitle: TextView = v.findViewById(R.id.textViewTitle)
        init {
            v.setOnClickListener{
                Log.d("List", "${this.layoutPosition}th item clicked")
                listener?.onItemClick(v, this.layoutPosition)
            }
        }
    }

    private var data = listOf<DiaryRecord>()
    fun updateData(data:List<DiaryRecord>){
        this.data = data
        notifyDataSetChanged()
    }

    fun getItem(position: Int):DiaryRecord{
        return data[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ListViewHolder(v, listener)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val record = data[position]
        holder.textViewDay.text = "${record.year}년 ${record.month}월 ${record.date}일"
        holder.textViewTitle.text = record.title
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
package com.example.penjualan_rimaantika

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.penjualan_rimaantika.room.tb_barang
import kotlinx.android.synthetic.main.adapter_barang.view.*

class AdapterBarang(private val barang: ArrayList<tb_barang>, private val listener: onAdapterListener)
    : RecyclerView.Adapter<AdapterBarang.BrgViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrgViewHolder {
        return BrgViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_barang,parent,false)
        )
    }

    override fun getItemCount() = barang.size

    override fun onBindViewHolder(holder: BrgViewHolder, position: Int) {
        val barang = barang[position]
        holder.view.IDBrg.text = barang.id_brg.toString()
        holder.view.NamaBrg.text = barang.nm_brg
        holder.view.IDBrg.setOnClickListener{
            listener.onClick(barang)
        }
        holder.view.icon_edit.setOnClickListener{
            listener.onUpdate(barang)
        }
        holder.view.icon_delete.setOnClickListener{
            listener.onDelete(barang)
        }

    }
    class BrgViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(list: List<tb_barang>){
        barang.clear()
        barang.addAll(list)
        notifyDataSetChanged()
    }
    interface onAdapterListener{
        fun onClick(barang: tb_barang)
        fun onUpdate(barang: tb_barang)
        fun onDelete(barang: tb_barang)
    }
}
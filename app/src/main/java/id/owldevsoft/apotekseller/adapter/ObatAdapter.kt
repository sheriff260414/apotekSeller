package id.owldevsoft.apotekseller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.JenisObatResponse
import id.owldevsoft.apotekseller.model.ObatResponse
import kotlinx.android.synthetic.main.item_data_obat.view.*

class ObatAdapter(
    private val item: ArrayList<ObatResponse.Data>?,
    val context: Context,
    private val clickActionObat: OnClickActionObat
) : RecyclerView.Adapter<ObatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObatAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_data_obat,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return item?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item?.get(position)!!, clickActionObat)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bind(item: ObatResponse.Data?, clickActionObat: OnClickActionObat) {
            view.tv_nama_obat.text = item?.namaObat
            view.tv_nama_obat.setOnClickListener{
                clickActionObat.onCLickDetail(item)
            }
            view.iv_edit_obat.setOnClickListener{
                clickActionObat.onCLickUpdate(item)
            }
            view.iv_delete_obat.setOnClickListener {
                clickActionObat.onClickDelete(item)
            }
        }
    }

    interface OnClickActionObat {
        fun onCLickDetail(position: ObatResponse.Data?)
        fun onCLickUpdate(position: ObatResponse.Data?)
        fun onClickDelete(position: ObatResponse.Data?)

    }

}
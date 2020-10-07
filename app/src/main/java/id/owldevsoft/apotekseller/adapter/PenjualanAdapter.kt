package id.owldevsoft.apotekseller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.PenjualanResponse
import kotlinx.android.synthetic.main.item_penjualan.view.*
import java.text.NumberFormat
import java.util.*

class PenjualanAdapter(
    val item: ArrayList<PenjualanResponse.Data>?,
    val context: Context,
    private val clickActionPenjualan: OnClickActionPenjualan
) : RecyclerView.Adapter<PenjualanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_penjualan,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return item?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item?.get(position)!!, clickActionPenjualan)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bind(item: PenjualanResponse.Data, clickActionPenjualan: OnClickActionPenjualan) {
            view.tv_nama_obat_penjualan.text = item.namaObat
            val harga = item.harga.toInt()
            view.tv_harga_penjualan.text = "Rp " + NumberFormat.getNumberInstance(Locale.US).format(harga)
            view.tv_satuan_penjualan.text = item.namaSatuan
            view.tv_kategori_penjualan.text = item.kategori
            view.iv_edit_obat_penjualan.setOnClickListener {
                clickActionPenjualan.onCLickUpdate(item)
            }
            view.iv_delete_obat_penjualan.setOnClickListener {
                clickActionPenjualan.onClickDelete(item)
            }
        }

    }

    interface OnClickActionPenjualan {
        fun onCLickUpdate(position: PenjualanResponse.Data?)
        fun onClickDelete(position: PenjualanResponse.Data?)
    }

}
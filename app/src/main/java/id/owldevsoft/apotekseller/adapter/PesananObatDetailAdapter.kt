package id.owldevsoft.apotekseller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.PesananObatDetailResponse
import kotlinx.android.synthetic.main.item_detail_pesanan.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class PesananObatDetailAdapter(
    val item: ArrayList<PesananObatDetailResponse.Data>?,
    val context: Context
) : RecyclerView.Adapter<PesananObatDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_detail_pesanan,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return item?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item?.get(position)!!)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val view = view
        fun bind(item: PesananObatDetailResponse.Data) {
            view.tv_nama_obat_detail_pesanan.text = item.namaObat
            view.tv_jumlah_obat_detail_pesanan.text = item.jumlah
            view.tv_harga_obat_detail_pesanan.text = "Rp " + NumberFormat.getNumberInstance(Locale.US).format(item.harga.toInt())
            view.tv_totalharga_obat_detail_pesanan.text = "Rp " + NumberFormat.getNumberInstance(Locale.US).format(item.total.toInt())
            view.tv_catatan_obat_detail_pesanan.text = item.catatan
        }
    }

}